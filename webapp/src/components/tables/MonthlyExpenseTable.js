import "../../styles/tables/MonthlyExpenseTable.css";

function cleanText(text) {
  if (!text) return "-";
  const trimmed = text.toString().trim();
  return trimmed.length === 0 ? "-" : trimmed;
}

function formatMoney(value) {
    if (value === null || value === undefined || value === "") return "-";
    return `$${Number(value).toFixed(2)}`;
}

export default function MonthlyExpenseTable ({monthlyExpenses = [], onUpdate, actionsDisabled}) {
    const sortedMonthlyExpenses = [...monthlyExpenses].sort((a, b) => {
        const monthA = a.month || "";
        const monthB = b.month || "";
        return monthB.localeCompare(monthA);
    });

    return (
        <div className = "monthlyExpense-table-wrapper">
            <table className = "monthlyExpense-table">
                <thead>
                    <tr>
                        <th>Month</th>
                        <th>Take Home Income</th>
                        <th>Emergency Fund</th>
                        <th>Srs</th>
                        <th>Ssb</th>
                        <th>Debt</th>
                        <th>Insurances</th>
                        <th>Bills & Utilities</th>
                        <th>Mortgage</th>
                        <th>Tax</th>
                        <th>Transport</th>
                        <th>Parent's Allowance</th>
                        <th>Food</th>
                        <th>Groceries</th>
                        <th>Haircut</th>
                        <th>Medical</th>
                        <th>Tithes</th>
                        <th>Overspent</th>
                        <th>Savings</th>
                    </tr>
                </thead>

                <tbody>
                    {sortedMonthlyExpenses.map((monthlyExpense) => (
                        <tr key = {monthlyExpense.month}>
                            <td>{cleanText(monthlyExpense.month)}</td>
                            <td>{formatMoney(monthlyExpense.income)}</td>
                            <td>{formatMoney(monthlyExpense.emergencyFund)}</td>
                            <td>{formatMoney(monthlyExpense.srs)}</td>
                            <td>{formatMoney(monthlyExpense.ssb)}</td>
                            <td>{formatMoney(monthlyExpense.debt)}</td>
                            <td>{formatMoney(monthlyExpense.insurances)}</td>
                            <td>{formatMoney(monthlyExpense.billsAndUtilities)}</td>
                            <td>{formatMoney(monthlyExpense.mortgage)}</td>
                            <td>{formatMoney(monthlyExpense.tax)}</td>
                            <td>{formatMoney(monthlyExpense.transport)}</td>
                            <td>{formatMoney(monthlyExpense.parentsAllowance)}</td>
                            <td>{formatMoney(monthlyExpense.food)}</td>
                            <td>{formatMoney(monthlyExpense.groceries)}</td>
                            <td>{formatMoney(monthlyExpense.haircut)}</td>
                            <td>{formatMoney(monthlyExpense.medical)}</td>
                            <td>{formatMoney(monthlyExpense.tithes)}</td>
                            <td>{formatMoney(monthlyExpense.overspent)}</td>
                            <td>{formatMoney(monthlyExpense.savings)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}