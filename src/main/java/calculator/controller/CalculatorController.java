package calculator.controller;

import calculator.service.CalculatorService;
import ui.InputView;
import ui.OutputView;
import util.Menu;

import java.util.function.Supplier;

public class CalculatorController {
    private InputView inputView;
    private OutputView outputView;
    private CalculatorService calculatorService;

    public CalculatorController(InputView inputView, OutputView outputView, CalculatorService calculatorService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.calculatorService = calculatorService;
    }

    public void start() {
        boolean continueCalculator = true;
        while(continueCalculator) {
            continueCalculator = eachExecute();
        }
    }

    private boolean eachExecute() {
        Menu select = userInput(()->{
            outputView.printMenu();
            return inputView.getMenuNumber();
        });

        return executeMenu(select);
    }

    private boolean executeMenu(Menu menu) {
        if (menu == Menu.END) {
            return false;
        }

        if (menu == Menu.SEARCH) {
            executeSearch();
            return true;
        }

        executeCalc();
        return true;
    }

    private void executeCalc() {
        Double result = userInput(()->{
            outputView.printEmptyMsg();
            return this.calculatorService.calculate(inputView.getEquation());
        });
        outputView.printResult(result);
    }

    private <T> T userInput(Supplier<T> input) {
        while (true) {
            try {
                return input.get();
            } catch (RuntimeException exception) {
                outputView.printErrorMsg(exception.getMessage());
            }
        }
    }

    private void executeSearch() {
        outputView.printCalculators(this.calculatorService.getCalculateList());
    }
}
