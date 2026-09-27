import "../../style/tables/DailyExpenseTable.css";   

export default function DailyExpenseAction ({dailyExpense, onUpdate, disabled}) {
    return (
        <div className = "action-buttons">
            <button
                onClick = {() => onUpdate(dailyExpense)}
                disabled = {disabled}
            >
                Update
            </button>
        </div>
    );
}