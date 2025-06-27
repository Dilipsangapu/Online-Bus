// agent.js (Fully functional and optimized for buses and routes)
let selectedBus = null;
let editingBusId = null;
let editingRouteId = null;

// Show section from sidebar
function showSection(sectionId) {
  document.querySelectorAll(".dashboard-section").forEach(sec => sec.classList.remove("active"));
  document.querySelectorAll(".sidebar li").forEach(li => li.classList.remove("active"));

  document.getElementById(sectionId + "Section").classList.add("active");
  document.querySelector(`.sidebar li[onclick*="${sectionId}"]`).classList.add("active");

  if (sectionId === "dashboard") loadDashboardStats();
  if (sectionId === "buses") fetchBuses();
  if (sectionId === "layout") fetchBusesForLayout();
  if (sectionId === "routes") loadRoutes();
}

function loadDashboardStats() {
  document.getElementById("totalTrips").innerText = Math.floor(Math.random() * 30) + 5;
  document.getElementById("monthlyRevenue").innerText = "₹" + (10000 + Math.floor(Math.random() * 50000));
}

// ---------------- BUS HANDLING ---------------- //
function fetchBuses() {
  const agentId = document.body.getAttribute("data-email");
  fetch(`/buses/api/by-operator/${agentId}`)
    .then(res => {
      if (!res.ok) throw new Error("Failed to fetch buses");
      return res.json();
    })
    .then(buses => {
      const tableBody = document.getElementById("busTableBody");
      tableBody.innerHTML = "";
      buses.forEach(bus => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${bus.busName}</td>
          <td>${bus.busNumber}</td>
          <td>${bus.busType}</td>
          <td>${bus.totalSeats}</td>
          <td><a href="/buses/edit/${bus.id}" class="btn-edit">Edit</a></td>
        `;
        tableBody.appendChild(row);
      });
    })
    .catch(err => console.error("Failed to load assigned buses", err));
}

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
  const url = editingBusId ? `/buses/api/update/${editingBusId}` : "/buses/api/add";

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
    .catch(err => console.error("Error saving bus:", err));
});

// ---------------- ROUTE HANDLING ---------------- //
function loadRoutes() {
  const email = document.body.getAttribute("data-email");
  fetch(`/buses/api/by-operator/${email}`)
    .then(res => res.json())
    .then(buses => {
      const routeList = document.getElementById("routeList");
      routeList.innerHTML = "";

      buses.forEach(bus => {
        fetch(`/api/routes/by-bus/${bus.id}`)
          .then(res => res.json())
          .then(routes => {
            if (routes.length === 0) return;

            const section = document.createElement("div");
            section.innerHTML = `<h4>🚌 ${bus.busName}</h4>`;

            const table = document.createElement("table");
            table.className = "route-table";
            table.innerHTML = `<thead><tr><th>From</th><th>To</th><th>Stops</th><th>Timings</th><th>Actions</th></tr></thead>`;

            const tbody = document.createElement("tbody");
            routes.forEach(route => {
              const row = document.createElement("tr");
              row.innerHTML = `
                <td>${route.from}</td>
                <td>${route.to}</td>
                <td>${route.stops.join(", ")}</td>
                <td>${route.timings}</td>
                <td>
                  <button onclick='editRoute(${JSON.stringify(route)})'>✏️</button>
                  <button onclick='deleteRoute("${route.id}")'>🗑️</button>
                </td>
              `;
              tbody.appendChild(row);
            });
            table.appendChild(tbody);
            section.appendChild(table);
            routeList.appendChild(section);
          });
      });
    });
}

function editRoute(route) {
  editingRouteId = route.id;
  const form = document.getElementById("routeForm");
  form.from.value = route.from;
  form.to.value = route.to;
  form.stops.value = route.stops.join(", ");
  form.timings.value = route.timings;
  selectedBus = { id: route.busId };
}

function deleteRoute(routeId) {
  fetch(`/api/routes/delete/${routeId}`, { method: "DELETE" })
    .then(() => loadRoutes());
}

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

  const method = editingRouteId ? "PUT" : "POST";
  const url = editingRouteId ? `/api/routes/update/${editingRouteId}` : "/api/routes/add";

  fetch(url, {
    method: method,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(route)
  })
    .then(res => res.json())
    .then(() => {
      form.reset();
      editingRouteId = null;
      loadRoutes();
    });
});

// ---------------- LAYOUT HANDLING ---------------- //
function fetchBusesForLayout() {
  const email = document.body.getAttribute("data-email");
  fetch(`/buses/api/by-operator/${email}`)
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
        card.querySelector(".configure-btn").addEventListener("click", () => {
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
    div.innerHTML = `${type === "sleeper" ? "🛎️" : "🧼"} S${i} <input type="number" placeholder="₹" value="500" />`;
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

document.addEventListener("DOMContentLoaded", () => {
  showSection("dashboard");
});
