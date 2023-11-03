package seedu.nuscents.commands;

import seedu.nuscents.data.TransactionList;
import seedu.nuscents.data.exception.NuscentsException;

public class BudgetCommand extends Command {

    private float budget;
    public BudgetCommand(float budget) {
        this.budget = budget;
    }

    @Override
    public void execute(TransactionList transactionList) throws NuscentsException {
        transactionList.setBudget(budget);
    }
}