package com.example.market;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MarketControllerTest {

    private MarketController marketController;

//CHATGPT
    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> {
            try {
                marketController = new MarketController();
                marketController.simpleGame = new CheckBox();
                marketController.generalGame = new CheckBox();
                // Simulate setting up the game with a valid board size (e.g., 3x3)
                marketController.initialize(null, null);
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS); // Wait for JavaFX initialization
    }



    //MANUAL
    @Test
    void onHelloButtonClick() {
        String replayMessage = "YAY REPLAY THIS SOS GAME!";
        assertEquals("YAY REPLAY THIS SOS GAME!", replayMessage);
    }

    @Test
    void initialize() {
        // Create an assert test for grid box = 8
        // Example assertion
        // assertEquals(8, marketController.getGridSize());
    }





    //CHATGPT
    @Test
    void onSimpleGameChecked_ShouldActivateSimpleGameMode() {
        Platform.runLater(() -> {
            // When the simple game mode is selected
            marketController.onSimpleGameChecked();

            // Then the simple game mode should be active and general mode should be inactive
            assertTrue(marketController.simpleGame.isSelected(), "Simple game mode should be selected.");
            assertFalse(marketController.generalGame.isSelected(), "General game mode should be deselected.");
        });
    }

    @Test
    void onGeneralGameChecked_ShouldActivateGeneralGameMode() {
        Platform.runLater(() -> {
            // When the general game mode is selected
            marketController.onGeneralGameChecked();

            // Then the general game mode should be active and simple mode should be inactive
            assertTrue(marketController.generalGame.isSelected(), "General game mode should be selected.");
            assertFalse(marketController.simpleGame.isSelected(), "Simple game mode should be deselected.");
        });
    }

    @Test
    void when_S_Moves_And_No_SSS_Game_Continues_And_Turn_Switches() {
        Platform.runLater(() -> {
            // Simulate S's move
            marketController.turn = true; // It's S's turn
            int row = 0, column = 0;
            StackPane cell = new StackPane();
            marketController.WhenCellisClicked(row, column, cell); // S places "S" at (0, 0)

            // Check that the turn switches to O
            assertFalse(marketController.turn); // Turn should now be O's turn
        });
    }

    @Test
    void when_O_Moves_And_No_OOO_Game_Continues_And_Turn_Switches() {
        Platform.runLater(() -> {
            // Simulate O's move
            marketController.turn = false; // It's O's turn
            int row = 1, column = 1;
            StackPane cell = new StackPane();
            marketController.WhenCellisClicked(row, column, cell); // O places "O" at (1, 1)

            // Check that the turn switches to S
            assertTrue(marketController.turn); // Turn should now be S's turn
        });
    }
}