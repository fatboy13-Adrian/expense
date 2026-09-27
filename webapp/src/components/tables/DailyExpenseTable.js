import "../../styles/tables/DailyExpenseTable.css";

function cleanText(text) {
  if (!text) return "-";
  const trimmed = text.toString().trim();
  return trimmed.length === 0 ? "-" : trimmed;
}

function formatMoney(value) {
    if (value === null || value === undefined || value === "") return "-";
    return `$${Number(value).toFixed(2)}`;
}

export default function DailyExpenseTable ({dailyExpenses = [] , onUpdate, actionsDisabled}) {
    return (
        <div className = "dailyExpense-table-wrapper">
            <table className = "dailyExpense-table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Public Transport</th>
                        <th>Private Transport</th>
                        <th>Haircut</th>
                        <th>Medical</th>
                        <th>Parent's Allowance</th>
                        <th>Breakfast</th>
                        <th>Lunch</th>
                        <th>Dinner</th>
                        <th>Groceries</th>
                        <th>Eating Out</th>
                        <th>Holiday</th>
                        <th>Recreational</th>
                        <th>Shopping</th>
                        <th>Sports</th>
                        <th>Tech</th>
                        <th>Tithes</th>
                    </tr>
                </thead>

                <tbody>
                    {(dailyExpenses || []).map((dailyExpense) => (
                        <tr key = {dailyExpense.date}>
                            <td>{cleanText(dailyExpense.date)}</td>
                            <td>{formatMoney(dailyExpense.publicTransport)}</td>
                            <td>{formatMoney(dailyExpense.privateTransport)}</td>
                            <td>{formatMoney(dailyExpense.haircut)}</td>
                            <td>{formatMoney(dailyExpense.medical)}</td>
                            <td>{formatMoney(dailyExpense.parentsAllowance)}</td>
                            <td>{formatMoney(dailyExpense.breakfast)}</td>
                            <td>{formatMoney(dailyExpense.lunch)}</td>
                            <td>{formatMoney(dailyExpense.dinner)}</td>
                            <td>{formatMoney(dailyExpense.groceries)}</td>
                            <td>{formatMoney(dailyExpense.eatingOut)}</td>
                            <td>{formatMoney(dailyExpense.holiday)}</td>
                            <td>{formatMoney(dailyExpense.recreational)}</td>
                            <td>{formatMoney(dailyExpense.shopping)}</td>
                            <td>{formatMoney(dailyExpense.sports)}</td>
                            <td>{formatMoney(dailyExpense.tech)}</td>
                            <td>{formatMoney(dailyExpense.tithes)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}