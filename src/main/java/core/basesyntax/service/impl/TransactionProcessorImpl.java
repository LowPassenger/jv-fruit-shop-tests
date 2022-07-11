package core.basesyntax.service.impl;

import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;

public class TransactionProcessorImpl implements TransactionProcessor {
    private static final int FRUIT_OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int FRUIT_QUANTITY_INDEX = 2;
    private Map<String, OperationHandler> handlerMap;

    public TransactionProcessorImpl(Map<String, OperationHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void process(List<String> transactions) {
        if (transactions == null) {
            throw new RuntimeException("FileReaderService sent a null List!");
        }
        if (handlerMap == null) {
            throw new RuntimeException("Can't choose an OperationHandler, Map is null!");
        }
        for (int i = 1; i < transactions.size(); i++) {
            if (transactions.get(i) == null) {
                throw new RuntimeException("FileReaderService sent a null value on "
                        + i + 2 + "line!");
            }
            if (transactions.get(i).isEmpty()) {
                throw new RuntimeException("FileReaderService sent an empty line on "
                        + i + 2 + "line!");
            }
            String[] splitedLine = transactions.get(i).split(",");
            if (handlerMap.get(splitedLine[FRUIT_OPERATION_INDEX]) == null) {
                throw new RuntimeException("OperationHandler unrecognized!");
            }
            handlerMap.get(splitedLine[FRUIT_OPERATION_INDEX]).handle(splitedLine[FRUIT_INDEX],
                    Integer.parseInt(splitedLine[FRUIT_QUANTITY_INDEX]));
        }
    }
}
