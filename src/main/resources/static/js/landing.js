// Enable the Search button only when all fields are filled
document.addEventListener("DOMContentLoaded", () => {
  const fromInput = document.querySelector('input[placeholder="Enter departure city"]');
  const toInput = document.querySelector('input[placeholder="Enter destination city"]');
  const dateInput = document.querySelector('input[type="date"]');
  const searchBtn = document.querySelector(".search-btn");
  const form = document.querySelector(".search-form");

  function checkInputs() {
    if (fromInput.value && toInput.value && dateInput.value) {
      searchBtn.disabled = false;
    } else {
      searchBtn.disabled = true;
    }
  }

  fromInput.addEventListener("input", checkInputs);
  toInput.addEventListener("input", checkInputs);
  dateInput.addEventListener("change", checkInputs);

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const from = fromInput.value.trim();
    const to = toInput.value.trim();
    const date = dateInput.value;

    try {
      const response = await fetch(`/buses/api/search?from=${encodeURIComponent(from)}&to=${encodeURIComponent(to)}&date=${date}`);
      const buses = await response.json();

      renderSearchResults(buses);
    } catch (err) {
      console.error("Search error:", err);
      document.getElementById("searchResults").innerHTML = "<p>❌ Failed to fetch search results.</p>";
    }
  });
});

// Render results dynamically below the form
function renderSearchResults(buses) {
  const resultDiv = document.getElementById("searchResults");
  resultDiv.innerHTML = "";

  if (!buses || buses.length === 0) {
    resultDiv.innerHTML = "<p>No buses found for your search.</p>";
    return;
  }

  buses.forEach(bus => {
    const card = document.createElement("div");
    card.className = "bus-card";
    card.innerHTML = `
      <h3>${bus.busName} (${bus.busType})</h3>
      <p><strong>Bus No:</strong> ${bus.busNumber}</p>
      <p><strong>Seats:</strong> ${bus.totalSeats}</p>
      <button class="book-btn" onclick="handleBooking('${bus.id}')">🚌 Book Now</button>
    `;
    resultDiv.appendChild(card);
  });
}

// Booking logic: redirect to login if not logged in
function handleBooking(busId) {
  const userEmail = document.querySelector('meta[name="user-email"]')?.content;

  if (!userEmail || userEmail.trim() === "") {
    alert("⚠️ Please login to book a bus.");
    window.location.href = "/login";
    return;
  }

  // ✅ Go to booking page
  window.location.href = `/book?busId=${busId}`;
}
