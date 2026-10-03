import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import DailyExpenseTable from "../components/tables/DailyExpenseTable";
import "../styles/dashboards/DailyExpenseDashboard.css";

export default function DailyExpenseDashboard () {
    const [dailyExpenses, setDailyExpenses] = useState([]);
    const [selectedMonth, setSelectedMonth] = useState(() => {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        return `${year}-${month}`;
    });

    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        setLoading(true);
        setError("");
        axios.get("http://localhost:8080/dailyExpenses")
            .then((res) => {
                setDailyExpenses(res.data);
            })
            .catch((error) => {
                setError(error.response?.data?.message || "Failed to retrieve daily expense list.");
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);

    const filteredDailyExpenses = dailyExpenses
    .filter((dailyExpense) => dailyExpense.date)
    .filter((dailyExpense) => {
        if (selectedMonth === "") return true;
        return dailyExpense.date.substring(0, 7) === selectedMonth;
    })
    .sort((a, b) => new Date(b.date) - new Date(a.date));

    const handleResetToCurrentMonth = () => {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        setSelectedMonth(`${year}-${month}`);
    };

    return (
        <div className = "DailyExpenseDashboard-center">
            <h1>View Daily Expense Records</h1>
            <div className = "filter-section">
                <SelectDate 
                    type = "month" 
                    value = {selectedMonth} 
                    onChange = {(e) => setSelectedMonth(e.target.value)} 
                    className = "input" 
                    max = {new Date().toISOString().substring(0, 7)}
                />
            </div> 

            {loading ? (
                <p>Loading...</p>
            ) : error ? (
                <div className = "error-message" role = "alert">
                    {error}
                </div>
            ) : (
                <>
                    <div className = "dailyExpense-table-wrapper">
                        <DailyExpenseTable dailyExpenses = {filteredDailyExpenses}/>
                    </div>

                    <div className = "buttons-center">
                        <button className = "custom-btn" onClick={() => navigate("/dailyExpenses/create")}>
                            New
                        </button>
                        <button className = "custom-btn" onClick = {() => setSelectedMonth("")}>
                            View By Month
                        </button>
                        <button className = "custom-btn" onClick={() => navigate("/dailyExpenses/update")}>
                            Edit
                        </button>
                        <button className = "custom-btn" onClick = {handleResetToCurrentMonth}>
                            Reset
                        </button>
                        <button className = "custom-btn" onClick={() => navigate("/home")}>
                            Home
                        </button>
                    </div>
                </>
            )}           
        </div>
    )
}