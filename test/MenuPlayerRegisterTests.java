import macaroni.app.menuView.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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

        var plumberName = "slububu";
        elements.requireItemCount(0);
        textBox.enterText(plumberName);
        submitButton.click();
        elements.requireItemCount(1);
        assertEquals(plumberName, elements.contents()[0]);
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

        var saboteurName = "wabalaba";
        elements.requireItemCount(0);
        textBox.enterText(saboteurName);
        submitButton.click();
        elements.requireItemCount(1);
        assertEquals(saboteurName, elements.contents()[0]);
    }
}
