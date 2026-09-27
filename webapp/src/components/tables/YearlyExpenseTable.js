import "../../styles/tables/YearlyExpenseTable.css";

function cleanText(text) {
  if (!text) return "-";
  const trimmed = text.toString().trim();
  return trimmed.length === 0 ? "-" : trimmed;
}

function formatMoney(value) {
    if (value === null || value === undefined || value === "") 
        return "-";

    return `$${Number(value).toFixed(2)}`;
}

export default function YearlyExpenseTable ({yearlyExpenses = [],}) {
    return (
        <div className= "yearlyExpense-table-wrapper">
            <table className= "yearlyExpense-table">
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
            </table>

            <tbody>
                {(yearlyExpenses || []).map((yearlyExpense, index) => (
                    <tr key = {index}>
                        <td>{cleanText(yearlyExpense.year)}</td>
                        <td>{formatMoney(yearlyExpense.income)}</td>
                        <td>{formatMoney(yearlyExpense.emergencyFund)}</td>
                        <td>{formatMoney(yearlyExpense.srs)}</td>
                        <td>{formatMoney(yearlyExpense.ssb)}</td>
                        <td>{formatMoney(yearlyExpense.debt)}</td>
                        <td>{formatMoney(yearlyExpense.insurances)}</td>
                        <td>{formatMoney(yearlyExpense.billsAndUtilities)}</td>
                        <td>{formatMoney(yearlyExpense.mortgage)}</td>
                        <td>{formatMoney(yearlyExpense.tax)}</td>
                        <td>{formatMoney(yearlyExpense.transport)}</td>
                        <td>{formatMoney(yearlyExpense.parentsAllowance)}</td>
                        <td>{formatMoney(yearlyExpense.food)}</td>
                        <td>{formatMoney(yearlyExpense.groceries)}</td>
                        <td>{formatMoney(yearlyExpense.haircut)}</td>
                        <td>{formatMoney(yearlyExpense.medical)}</td>
                        <td>{formatMoney(yearlyExpense.tithes)}</td>
                        <td>{formatMoney(yearlyExpense.overspent)}</td>
                        <td>{formatMoney(yearlyExpense.savings)}</td>
                    </tr>
                ))}
            </tbody>
        </div>
    );
}