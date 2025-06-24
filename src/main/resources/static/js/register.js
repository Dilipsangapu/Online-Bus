function sendOtp() {
  const form = document.getElementById("registerForm");
  const name = form.querySelector("input[name='name']").value.trim();
  const phone = form.querySelector("input[name='phone']").value.trim();
  const email = form.querySelector("input[name='email']").value.trim();
  const age = form.querySelector("input[name='age']").value.trim();
  const gender = form.querySelector("select[name='gender']").value.trim();
  const password = form.querySelector("input[name='password']").value.trim();
  const confirmPassword = form.querySelector("input[name='confirmPassword']").value.trim();

  if (!name || !phone || !email || !age || !gender || !password || !confirmPassword) {
    alert("❌ Please fill all fields before sending OTP.");
    return;
  }

  if (password !== confirmPassword) {
    alert("❌ Passwords do not match.");
    return;
  }

  fetch("/api/auth/send-otp", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email })
  })
    .then(res => res.text().then(text => {
      if (!res.ok) throw new Error(text);
      alert("✅ " + text);
      document.getElementById("otpSection").style.display = "block";
      document.getElementById("verifyBtn").style.display = "block";
    }))
    .catch(err => alert("❌ " + err.message));
}

function verifyOtp() {
  const email = document.getElementById("registerForm").querySelector("input[name='email']").value;
  const otp = document.getElementById("otpInput").value.trim();

  if (!otp) {
    alert("❌ Please enter the OTP sent to your email.");
    return;
  }

  fetch("/api/auth/verify-otp", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, otp })
  })
    .then(res => res.text().then(text => {
      if (!res.ok) throw new Error(text);
      alert("✅ " + text);
      document.getElementById("finalRegisterBtn").style.display = "block";
      document.getElementById("verifyBtn").style.display = "none";
    }))
    .catch(err => alert("❌ " + err.message));
}

function registerUser() {
  const form = document.getElementById("registerForm");

  const user = {
    name: form.name.value,
    phone: form.phone.value,
    email: form.email.value,
    age: parseInt(form.age.value),
    gender: form.gender.value,
    password: form.password.value
  };

  fetch("/api/auth/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(user)
  })
    .then(res => res.text().then(text => {
      if (!res.ok) throw new Error(text);
      alert("✅ " + text);
      window.location.href = "/login";
    }))
    .catch(err => alert("❌ " + err.message));
}
