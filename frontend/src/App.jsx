import { useState } from 'react';
import { dashboardSummary, makePayment, markAttendance, membershipStatus, registerMember } from './api';

const membershipTypes = ['MONTHLY', 'QUARTERLY', 'YEARLY'];

export default function App() {
  const [registerForm, setRegisterForm] = useState({ fullName: '', phone: '', email: '', membershipType: 'MONTHLY' });
  const [paymentForm, setPaymentForm] = useState({ memberId: '', membershipType: 'MONTHLY', amount: '' });
  const [attendanceForm, setAttendanceForm] = useState({ memberId: '', source: 'QR', markedBy: 'gate-system' });
  const [statusMemberId, setStatusMemberId] = useState('');
  const [result, setResult] = useState(null);

  async function handleRegister(e) {
    e.preventDefault();
    setResult(await registerMember(registerForm));
  }

  async function handlePayment(e) {
    e.preventDefault();
    setResult(await makePayment({ ...paymentForm, memberId: Number(paymentForm.memberId), amount: Number(paymentForm.amount) }));
  }

  async function handleAttendance(e) {
    e.preventDefault();
    setResult(await markAttendance({ ...attendanceForm, memberId: Number(attendanceForm.memberId) }));
  }

  async function handleStatus() {
    setResult(await membershipStatus(Number(statusMemberId)));
  }

  async function handleDashboard() {
    setResult(await dashboardSummary());
  }

  return (
    <div className="container py-4">
      <h1 className="mb-4">Smart Gym Management System</h1>
      <p className="text-muted">Core Rule: 26 attendance days = 1 valid month</p>

      <div className="row g-4">
        <div className="col-lg-6">
          <div className="card p-3 h-100">
            <h5>Register Member</h5>
            <form onSubmit={handleRegister} className="d-flex flex-column gap-2">
              <input className="form-control" placeholder="Full Name" required value={registerForm.fullName} onChange={(e) => setRegisterForm({ ...registerForm, fullName: e.target.value })} />
              <input className="form-control" placeholder="Phone" required value={registerForm.phone} onChange={(e) => setRegisterForm({ ...registerForm, phone: e.target.value })} />
              <input className="form-control" placeholder="Email" required value={registerForm.email} onChange={(e) => setRegisterForm({ ...registerForm, email: e.target.value })} />
              <select className="form-select" value={registerForm.membershipType} onChange={(e) => setRegisterForm({ ...registerForm, membershipType: e.target.value })}>
                {membershipTypes.map((type) => <option key={type}>{type}</option>)}
              </select>
              <button className="btn btn-primary" type="submit">Register</button>
            </form>
          </div>
        </div>

        <div className="col-lg-6">
          <div className="card p-3 h-100">
            <h5>Advance Payment</h5>
            <form onSubmit={handlePayment} className="d-flex flex-column gap-2">
              <input className="form-control" placeholder="Member ID" required value={paymentForm.memberId} onChange={(e) => setPaymentForm({ ...paymentForm, memberId: e.target.value })} />
              <select className="form-select" value={paymentForm.membershipType} onChange={(e) => setPaymentForm({ ...paymentForm, membershipType: e.target.value })}>
                {membershipTypes.map((type) => <option key={type}>{type}</option>)}
              </select>
              <input className="form-control" placeholder="Amount" required value={paymentForm.amount} onChange={(e) => setPaymentForm({ ...paymentForm, amount: e.target.value })} />
              <button className="btn btn-success" type="submit">Pay</button>
            </form>
          </div>
        </div>

        <div className="col-lg-6">
          <div className="card p-3 h-100">
            <h5>Mark Attendance</h5>
            <form onSubmit={handleAttendance} className="d-flex flex-column gap-2">
              <input className="form-control" placeholder="Member ID" required value={attendanceForm.memberId} onChange={(e) => setAttendanceForm({ ...attendanceForm, memberId: e.target.value })} />
              <select className="form-select" value={attendanceForm.source} onChange={(e) => setAttendanceForm({ ...attendanceForm, source: e.target.value })}>
                <option>QR</option>
                <option>MANUAL</option>
              </select>
              <input className="form-control" placeholder="Marked By" required value={attendanceForm.markedBy} onChange={(e) => setAttendanceForm({ ...attendanceForm, markedBy: e.target.value })} />
              <button className="btn btn-warning" type="submit">Record Visit</button>
            </form>
          </div>
        </div>

        <div className="col-lg-6">
          <div className="card p-3 h-100 d-flex flex-column gap-2">
            <h5>Status & Dashboard</h5>
            <input className="form-control" placeholder="Member ID" value={statusMemberId} onChange={(e) => setStatusMemberId(e.target.value)} />
            <button className="btn btn-info" onClick={handleStatus}>Get Membership Status</button>
            <button className="btn btn-dark" onClick={handleDashboard}>Load Dashboard Summary</button>
          </div>
        </div>
      </div>

      <div className="mt-4">
        <h5>Response</h5>
        <pre className="result-box">{result ? JSON.stringify(result, null, 2) : 'No response yet.'}</pre>
      </div>
    </div>
  );
}
