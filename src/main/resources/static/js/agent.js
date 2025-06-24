let selectedBus = null;
let editingBusId = null;

// Show section from sidebar
function showSection(sectionId) {
  document.querySelectorAll(".dashboard-section").forEach(sec => sec.classList.remove("active"));
  document.querySelectorAll(".sidebar li").forEach(li => li.classList.remove("active"));

  document.getElementById(sectionId + "Section").classList.add("active");
  document.querySelector(`.sidebar li[onclick*="${sectionId}"]`).classList.add("active");

  if (sectionId === "dashboard") loadDashboardStats();
  if (sectionId === "buses") fetchBuses();
  if (sectionId === "layout") fetchBusesForLayout();
}

// Dashboard dummy stats
function loadDashboardStats() {
  document.getElementById("totalTrips").innerText = Math.floor(Math.random() * 30) + 5;
  document.getElementById("monthlyRevenue").innerText = "₹" + (10000 + Math.floor(Math.random() * 50000));
}

// Handle bus add/edit form submit
document.getElementById("busForm")?.addEventListener("submit", e => {
  e.preventDefault();
  const form = e.target;

  const bus = {
    operatorId: document.body.getAttribute("data-email"),
    operatorName: document.body.getAttribute("data-name"),
    busName: form.busName.value.trim(),
    busNumber: form.busNumber.value.trim(),
    busType: form.busType.value,
    deckType: form.deckType.value,
    sleeperCount: parseInt(form.sleeperSeats.value || 0),
    seaterCount: parseInt(form.seaterSeats.value || 0),
    totalSeats: (parseInt(form.sleeperSeats.value || 0) + parseInt(form.seaterSeats.value || 0)),
    hasUpperDeck: form.deckType.value.includes("Upper"),
    hasLowerDeck: true
  };

  const method = editingBusId ? "PUT" : "POST";
  const url = editingBusId
    ? `/api/buses/update/${editingBusId}`
    : "/api/buses/add";

  fetch(url, {
    method: method,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(bus)
  })
    .then(res => res.text())
    .then(msg => {
      alert(msg);
      form.reset();
      editingBusId = null;
      fetchBuses();
    })
    .catch(err => {
      console.error("Error saving bus:", err);
    });
});

// Fetch and display buses
function fetchBuses() {
  const agentId = document.body.getAttribute("data-email");
  fetch(`/api/buses/by-operator/${agentId}`)
    .then(res => {
      if (!res.ok) throw new Error("Failed to fetch buses");
      return res.json();
    })
    .then(buses => {
      const tableBody = document.getElementById("busTableBody");
      if (!tableBody) return;

      tableBody.innerHTML = "";
      buses.forEach(bus => {
        const row = document.createElement("tr");

        const encodedBus = encodeURIComponent(JSON.stringify(bus));

        row.innerHTML = `
          <td>${bus.busName}</td>
          <td>${bus.busNumber}</td>
          <td>${bus.busType}</td>
          <td>${bus.totalSeats}</td>
          <td>
            <button onclick='editBus(decodeURIComponent("${encodedBus}"))'>Edit</button>
          </td>
        `;
        tableBody.appendChild(row);
      });
    })
    .catch(err => {
      console.error("Failed to load assigned buses", err);
    });
}

// Populate form for editing
function editBus(raw) {
  const bus = JSON.parse(raw);
  const form = document.getElementById("busForm");
  form.busName.value = bus.busName;
  form.busNumber.value = bus.busNumber;
  form.busType.value = bus.busType;
  form.deckType.value = bus.deckType;
  form.sleeperSeats.value = bus.sleeperCount;
  form.seaterSeats.value = bus.seaterCount;
  editingBusId = bus.id;

  // Scroll to form and show section
  showSection("buses");
  form.scrollIntoView({ behavior: "smooth" });
}

// Cancel edit
function cancelEdit() {
  const form = document.getElementById("busForm");
  form.reset();
  editingBusId = null;
}

// Route handling
document.getElementById("routeForm")?.addEventListener("submit", e => {
  e.preventDefault();
  const form = e.target;
  const route = {
    busId: selectedBus?.id || "",
    from: form.from.value,
    to: form.to.value,
    stops: form.stops.value.split(",").map(s => s.trim()),
    timings: form.timings.value
  };

  fetch("/api/routes/add", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(route)
  })
    .then(res => res.json())
    .then(data => {
      const div = document.createElement("div");
      div.innerText = `🛣️ ${data.from} → ${data.to} via ${data.stops.join(", ")} [${data.timings}]`;
      document.getElementById("routeList").appendChild(div);
      form.reset();
    });
});

// Staff handling
document.getElementById("staffForm")?.addEventListener("submit", e => {
  e.preventDefault();
  const form = e.target;
  const staff = {
    busId: selectedBus?.id || "",
    driverName: form.driverName.value,
    driverContact: form.driverContact.value,
    conductorName: form.conductorName.value,
    conductorContact: form.conductorContact.value
  };

  fetch("/api/staff/add", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(staff)
  })
    .then(res => res.json())
    .then(data => {
      const box = document.createElement("div");
      box.className = "bus-card";
      box.innerHTML = `<strong>Driver:</strong> ${data.driverName} (${data.driverContact})<br>
                       <strong>Conductor:</strong> ${data.conductorName} (${data.conductorContact})`;
      document.getElementById("staffList").appendChild(box);
      form.reset();
    });
});

// Schedule handling
document.getElementById("scheduleForm")?.addEventListener("submit", e => {
  e.preventDefault();
  const form = e.target;
  const schedule = {
    busId: selectedBus?.id || "",
    routeId: form.route.value,
    days: form.days.value
  };

  fetch("/api/schedules/add", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(schedule)
  })
    .then(res => res.json())
    .then(data => {
      const item = document.createElement("div");
      item.className = "bus-card";
      item.innerText = `🕒 Route: ${data.routeId} → ${data.days}`;
      document.getElementById("tripScheduleList").appendChild(item);
      form.reset();
    });
});

// Layout
function fetchBusesForLayout() {
  const email = document.body.getAttribute("data-email");
  fetch(`/api/buses/by-operator/${email}`)
    .then(res => res.json())
    .then(buses => {
      const container = document.getElementById("seatLayoutBusCards");
      container.innerHTML = "";

      buses.forEach(bus => {
        const card = document.createElement("div");
        card.className = "bus-card";
        card.innerHTML = `
          <h4>${bus.busName}</h4>
          <p>${bus.busNumber} | ${bus.busType}</p>
          <p>Deck: ${bus.deckType}</p>
          <p>Seats: ${bus.totalSeats} (S: ${bus.sleeperCount}, T: ${bus.seaterCount})</p>
          <button class="configure-btn">Configure Seats</button>
        `;
        const btn = card.querySelector(".configure-btn");
        btn.addEventListener("click", () => {
          selectedBus = bus;
          renderRedbusLayout(bus);
        });

        container.appendChild(card);
      });
    });
}

function renderRedbusLayout(bus) {
  const lowerDeck = document.getElementById("lowerDeck");
  const upperDeck = document.getElementById("upperDeck");
  lowerDeck.innerHTML = "";
  upperDeck.innerHTML = "";

  const totalSeats = bus.sleeperCount + bus.seaterCount;

  for (let i = 1; i <= totalSeats; i++) {
    const type = i <= bus.sleeperCount ? "sleeper" : "seater";
    const deck = bus.deckType.includes("Upper") && i % 2 === 0 ? "upper" : "lower";

    const div = document.createElement("div");
    div.className = `seat ${type}`;
    div.innerHTML = `
      ${type === "sleeper" ? "🛏️" : "🪑"} S${i}
      <input type="number" placeholder="₹" value="500" />
    `;

    (deck === "upper" ? upperDeck : lowerDeck).appendChild(div);
  }

  switchDeck("lower");
}

function switchDeck(deck) {
  document.getElementById("lowerDeck").style.display = deck === "lower" ? "grid" : "none";
  document.getElementById("upperDeck").style.display = deck === "upper" ? "grid" : "none";
  document.getElementById("lowerTab").classList.toggle("active", deck === "lower");
  document.getElementById("upperTab").classList.toggle("active", deck === "upper");
}

// On load
document.addEventListener("DOMContentLoaded", () => {
  showSection("dashboard");
});
