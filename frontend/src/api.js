const API_BASE = 'http://localhost:8080/api';

export async function registerMember(payload) {
  const response = await fetch(`${API_BASE}/members/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  return response.json();
}

export async function makePayment(payload) {
  const response = await fetch(`${API_BASE}/payments/advance`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  return response.json();
}

export async function markAttendance(payload) {
  const response = await fetch(`${API_BASE}/attendance/mark`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  return response.json();
}

export async function membershipStatus(memberId) {
  const response = await fetch(`${API_BASE}/members/${memberId}/membership-status`);
  return response.json();
}

export async function dashboardSummary() {
  const response = await fetch(`${API_BASE}/dashboard/summary`);
  return response.json();
}
