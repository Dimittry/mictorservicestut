package com.microservicestut.client.mood;

import com.microservicestut.infrastructure.MessageListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static java.time.LocalTime.now;

public class HappinessChartData implements MessageListener<TweetMood> {
    private final XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
    private final Map<Integer, IncrementableIntegerProperty> minuteToDataPosition = new HashMap<>();

    public HappinessChartData() {
        int nowMinute = LocalDateTime.now().getMinute();
        IntStream.range(nowMinute, nowMinute + 10).forEach(new IntConsumer(){
            @Override
            public void accept(int value) {
                initialiseBarToZero(value);
            }
        });
    }

    @Override
    public void onMessage(TweetMood message) {
        if(message.isHappy()) {
            int x = now().getMinute();
            minuteToDataPosition.get(x).increment();
        }
    }

    public XYChart.Series<String, Number> getDataSeries() {
        return dataSeries;
    }

    private void initialiseBarToZero(int minute) {
        XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(minute), 0);
        IncrementableIntegerProperty incrementableIntegerProperty
                = new IncrementableIntegerProperty();
        data.YValueProperty().bindBidirectional(incrementableIntegerProperty);
        dataSeries.getData().add(data);
        minuteToDataPosition.put(minute, incrementableIntegerProperty);
    }

    private class IncrementableIntegerProperty extends SimpleIntegerProperty {
        private AtomicInteger value = new AtomicInteger(0);

        public void increment() {
            super.set(value.incrementAndGet());
        }
    }
}
