import macaroni.app.menuView.*;
import org.assertj.swing.core.KeyPressInfo;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static java.awt.event.KeyEvent.VK_ENTER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuPlayerRegisterTests extends UiTest {
    @Test
    public void addPlayerToPlumberTeam() {
        var plumberPanel = window.panel(matchClass(TeamPanel.class, (panel) ->
                Arrays.stream(panel.getComponents())
                        .anyMatch(c -> c instanceof MenuLabel ml && ml.getText().equals("Plumbers"))
        ));
        var elements = plumberPanel.list(matchClass(MenuList.class));
        var textBox = plumberPanel.textBox(matchClass(TeamTextBox.class));
        var submitButton = plumberPanel.button(matchClass(MenuTeamButton.class));

        var plumberName1 = "dibu";
        elements.requireItemCount(0);
        textBox.enterText(plumberName1);
        submitButton.click();
        elements.requireItemCount(1);
        assertEquals(plumberName1, elements.contents()[0]);

        var plumberName2 = "mibu";
        elements.requireItemCount(1);
        textBox.enterText(plumberName2).pressAndReleaseKey(KeyPressInfo.keyCode(VK_ENTER));
        elements.requireItemCount(2);
        assertEquals(plumberName2, elements.contents()[1]);
    }

    @Test
    public void addPlayerToSaboteurTeam() {
        var saboteurPanel = window.panel(matchClass(TeamPanel.class, (panel) ->
                Arrays.stream(panel.getComponents())
                        .anyMatch(c -> c instanceof MenuLabel ml && ml.getText().equals("Saboteurs"))
        ));
        var elements = saboteurPanel.list(matchClass(MenuList.class));
        var textBox = saboteurPanel.textBox(matchClass(TeamTextBox.class));
        var submitButton = saboteurPanel.button(matchClass(MenuTeamButton.class));

        var saboteurName1 = "subulubu";
        elements.requireItemCount(0);
        textBox.enterText(saboteurName1);
        submitButton.click();
        elements.requireItemCount(1);
        assertEquals(saboteurName1, elements.contents()[0]);

        var saboteurName2 = "wabalaba";
        elements.requireItemCount(1);
        textBox.enterText(saboteurName2).pressAndReleaseKey(KeyPressInfo.keyCode(VK_ENTER));
        elements.requireItemCount(2);
        assertEquals(saboteurName2, elements.contents()[1]);
    }
}
