import macaroni.app.App;
import macaroni.app.Window;
import macaroni.app.menuView.*;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UiTest {
    private FrameFixture window;

    @BeforeAll
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp() {
        Window wnd = GuiActionRunner.execute(() -> new App().getWindow());
        window = new FrameFixture(wnd);
        window.show();
    }

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
                        .anyMatch(c -> c instanceof MenuLabel ml && ml.getText().equals("Plumbers"))
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

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }

    private <T extends Component> GenericTypeMatcher<T> matchClass(Class<T> tClass) {
        return matchClass(tClass, t -> true);
    }

    private <T extends Component> GenericTypeMatcher<T> matchClass(Class<T> tClass, Function<T, Boolean> filter) {
        return new GenericTypeMatcher<>(tClass) {
            @Override
            protected boolean isMatching(T t) {
                return filter.apply(t);
            }
        };
    }
}
