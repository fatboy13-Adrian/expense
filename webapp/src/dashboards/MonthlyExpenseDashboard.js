import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import MonthlyExpenseTable from "../components/tables/MonthlyExpenseTable";
import "../styles/dashboards/MonthlyExpenseDashboard.css";
import SelectMonth from '../components/selections/SelectMonth';

export default function MonthlyExpenseDashboard () {
    const [monthlyExpenses, setMonthlyExpenses] = useState([]);
    const [month, setMonth] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        setLoading(true);
        setError("");
        axios.get("http://localhost:8080/monthlyExpenses")
            .then((res) => {
                setMonthlyExpenses(res.data);
            })
            .catch((error) => {
                setError(error.response?.data?.message || "Failed to retrieve monthly expense records.");
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);

    const filteredMonthlyExpenses = monthlyExpenses
    .filter((monthlyExpenses) => monthlyExpense.month)
    .filter((monthlyExpenses) => {
        const monthlyExpenseMonth = new Date(monthlyExpense.month);
        const matchedMonths = month === "" || monthlyExpenseMonth.toISOString().slice(0,7) === month;
        return matchedMonths
    });

    const downloadExcel = async () => {
        const year = new Date().getFullYear();
        const response = await fetch(`http://localhost:8080/monthlyExpenses/export/${year}`, {
            method: "GET"
        });

        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement("a");
        link.href = url;
        link.download = `Monthly Expense Records For Year ${year}.xlsx`;
        document.body.appendChild(link);
        link.click();
        link.remove();
        window.URL.revokeObjectURL(url);
    };

    return (
        <div className = "budgetDashboard-center">
            <h1>View Monthly Expense Records</h1>
            <div className = "filter-section">
                <SelectMonth name = "month" value = {month} onChange = {(e) => setMonth(e.target.value)}/>
            </div>
            {loading ? (
                <p>Loading...</p>
            ) : error ? (
                <div className = "error-message" role = "alert">
                    {error}
                </div>
            ) : (
                <>
                    <div className = "monthlyExpenseDashboard-table-wrapper">
                        <MonthlyExpenseTable monthlyExpenses = {filteredMonthlyExpenses}/>
                    </div>
                    <div className = "buttons-center">
                        <button className = "custom-btn" onClick = {() => navigate("/monthlyExpenses/create")}>
                            New
                        </button>
                        <button className = "custom-btn" onClick = {() => navigate("/monthlyExpenses/update")}>
                            Edit
                        </button>
                        <button className = "custom-btn" onClick = {() => setMonth("")}>
                            Clear
                        </button>
                        <button className = "custom-btn" onClick = {downloadExcel}>
                            Download
                        </button>
                        <button className = "custom-btn" onClick = {() => navigate("/home")}>
                            Home
                        </button>
                    </div>
                </>
            )}
        </div>
    );
}