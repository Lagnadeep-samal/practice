import { useState } from "react";
import api from "../api/axios";

function LoanApplicationForm() {
  const [formData, setFormData] = useState({
    customerName: "",
    loanType: "PERSONAL",
    loanAmount: "",
    outstandingAmount: "",
    interestRate: "",
  });
  const [status, setStatus] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setStatus(null);
    try {
      await api.post("/api/loans", {
        customerName: formData.customerName,
        loanType: formData.loanType,
        loanAmount: Number(formData.loanAmount),
        outstandingAmount: Number(formData.outstandingAmount),
        interestRate: Number(formData.interestRate),
      });
      setStatus("success");
      setFormData({
        customerName: "",
        loanType: "PERSONAL",
        loanAmount: "",
        outstandingAmount: "",
        interestRate: "",
      });
    } catch (err) {
      console.error(err);
      setStatus("error");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 420, margin: "40px auto", fontFamily: "sans-serif" }}>
      <h2>Create Loan</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: 12 }}>
          <label>Customer Name</label>
          <input type="text" name="customerName" value={formData.customerName} onChange={handleChange} required style={{ width: "100%", padding: 8 }} />
        </div>
        <div style={{ marginBottom: 12 }}>
          <label>Loan Type</label>
          <select name="loanType" value={formData.loanType} onChange={handleChange} style={{ width: "100%", padding: 8 }}>
            <option value="HOME">HOME</option>
            <option value="AUTO">AUTO</option>
            <option value="PERSONAL">PERSONAL</option>
          </select>
        </div>
        <div style={{ marginBottom: 12 }}>
          <label>Loan Amount</label>
          <input type="number" name="loanAmount" value={formData.loanAmount} onChange={handleChange} required min="1" style={{ width: "100%", padding: 8 }} />
        </div>
        <div style={{ marginBottom: 12 }}>
          <label>Outstanding Amount</label>
          <input type="number" name="outstandingAmount" value={formData.outstandingAmount} onChange={handleChange} required min="0" style={{ width: "100%", padding: 8 }} />
        </div>
        <div style={{ marginBottom: 12 }}>
          <label>Interest Rate (%)</label>
          <input type="number" step="0.01" name="interestRate" value={formData.interestRate} onChange={handleChange} style={{ width: "100%", padding: 8 }} />
        </div>
        <button type="submit" disabled={loading} style={{ padding: "10px 20px" }}>
          {loading ? "Submitting..." : "Create Loan"}
        </button>
        {status === "success" && <p style={{ color: "green" }}>Loan created successfully!</p>}
        {status === "error" && <p style={{ color: "red" }}>Something went wrong. Please try again.</p>}
      </form>
    </div>
  );
}

export default LoanApplicationForm;