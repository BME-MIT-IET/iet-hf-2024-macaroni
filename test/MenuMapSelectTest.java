import macaroni.app.menuView.MainMenu;
import macaroni.app.menuView.MenuLabel;
import macaroni.app.menuView.MenuList;
import macaroni.app.menuView.MenuPanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuMapSelectTest extends UiTest {
    private File tempMapFile;

    @BeforeEach
    public void setUp() {
        try {
            tempMapFile = File.createTempFile("temp-", ".map", new File("./assets/maps"));
            super.setUp();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void selectMap() {
        var menuPanel = window.panel(matchClass(MainMenu.class));
        var mapPanel = menuPanel.panel(matchClass(MenuPanel.class, (panel) ->
                Arrays.stream(panel.getComponents())
                        .anyMatch(c -> c instanceof MenuLabel ml && ml.getText().equals("Maps"))
        ));
        var elements = mapPanel.list(matchClass(MenuList.class));

        var fileName = tempMapFile.getName();
        var mapName = fileName.substring(0, fileName.lastIndexOf('.'));
        assertTrue(Arrays.asList(elements.contents()).contains(mapName));

        elements.requireNoSelection();
        elements.clickItem(mapName);
        elements.requireSelection(mapName);
        assertEquals(mapName, elements.targetCastedTo(MenuList.class).getSelectedElement());
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
        tempMapFile.deleteOnExit();
    }
}
