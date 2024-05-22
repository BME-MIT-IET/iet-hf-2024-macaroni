import macaroni.app.gameView.GamePanel;
import macaroni.app.menuView.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class StartGameTest extends UiTest {
    private File tempMapFile;

    @BeforeEach
    public void setUp() {
        try {
            tempMapFile = File.createTempFile("temp-", ".map", new File("./assets/maps"));
            try (var fileOutputStream = new FileOutputStream(tempMapFile)) {
                Files.copy(Path.of("./test/test.map"), fileOutputStream);
            }
            super.setUp();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void startGame() {
        var menuPanel = window.panel(matchClass(MainMenu.class));
        var plumberPanel = menuPanel.panel(matchClass(TeamPanel.class, (panel) ->
                Arrays.stream(panel.getComponents())
                        .anyMatch(c -> c instanceof MenuLabel ml && ml.getText().equals("Plumbers"))
        ));
        var plumberTextBox = plumberPanel.textBox(matchClass(TeamTextBox.class));
        var plumberSubmitButton = plumberPanel.button(matchClass(MenuTeamButton.class));

        plumberTextBox.setText("bob");
        plumberSubmitButton.click();
        plumberTextBox.setText("mario");
        plumberSubmitButton.click();

        var saboteurPanel = menuPanel.panel(matchClass(TeamPanel.class, (panel) ->
                Arrays.stream(panel.getComponents())
                        .anyMatch(c -> c instanceof MenuLabel ml && ml.getText().equals("Saboteurs"))
        ));
        var saboteurTextBox = saboteurPanel.textBox(matchClass(TeamTextBox.class));
        var saboteurSubmitButton = saboteurPanel.button(matchClass(MenuTeamButton.class));

        saboteurTextBox.setText("swiper");
        saboteurSubmitButton.click();
        saboteurTextBox.setText("robi");
        saboteurSubmitButton.click();

        var mapPanel = menuPanel.panel(matchClass(MenuPanel.class, (panel) ->
                Arrays.stream(panel.getComponents())
                        .anyMatch(c -> c instanceof MenuLabel ml && ml.getText().equals("Maps"))
        ));
        var mapElements = mapPanel.list(matchClass(MenuList.class));
        var fileName = tempMapFile.getName();
        var mapName = fileName.substring(0, fileName.lastIndexOf('.'));

        mapElements.clickItem(mapName);

        var startButton = menuPanel.button(matchClass(MenuStartButton.class));
        var gamePanel = window.panel(matchClass(GamePanel.class));

        menuPanel.requireVisible();
        startButton.click();
        menuPanel.requireNotVisible();
        gamePanel.requireVisible();
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
        tempMapFile.deleteOnExit();
    }
}
