package macaroni.actions;

import macaroni.app.gameView.ViewRepository;
import macaroni.model.character.Character;
import macaroni.model.character.Plumber;
import macaroni.model.effects.NoEffect;
import macaroni.model.element.Element;
import macaroni.model.element.Pipe;
import macaroni.model.element.Pump;
import macaroni.views.CharacterView;
import macaroni.views.PipeView;
import macaroni.views.Position;
import macaroni.views.PumpView;

/**
 * Mozgási interakciót kezelő osztály.
 */
public class MoveAction extends Action {

    /**
     * A karakter, ami a mozgást végzi.
     */
    private final Character actor;

    /**
     * Létrehoz egy MoveAction példányt.
     *
     * @param actor A karakter, ami a mozgást végzi.
     */
    public MoveAction(Character actor) {
        this.actor = actor;
    }

    /**
     * Ha tudja, elvégzi a játékos paraméterként kapott elemre való mozgatását.
     *
     * @param element az elem, amin action-t szeretnénk végezni.
     * @return Igazzal tér vissza ha sikeres.
     */
    @Override
    public boolean doAction(Element element) {
        var locationBeforeMove = actor.getLocation();
        var success = actor.moveTo(element);
        System.out.println("Move success: " + success);
        if (success) {
            var characterView = (CharacterView) ViewRepository.getViewOfObject(actor);
            characterView.setPosition(ViewRepository.getViewOfObject(element).getPosition());
            if (actor instanceof Plumber plumber) {
                var heldPipe = plumber.getHeldPipe();
                if (heldPipe != null) {
                    var heldPipeView = (PipeView) ViewRepository.getViewOfObject(heldPipe);
                    var endpoint1 = ViewRepository.getViewOfObject(locationBeforeMove).getPosition();
                    var endpoint2 = ViewRepository.getViewOfObject(actor.getLocation()).getPosition();

                    heldPipeView.replaceEndpointPos(endpoint1, endpoint2);

                    var effect = heldPipe.getEffect();
                    if (!(effect instanceof NoEffect)) {
                        var v = ViewRepository.getViewOfObject(effect);
                        v.setPosition(heldPipeView.getPosition());
                    }

                    var connectedElement = heldPipe.getEndpoint(0) != null ? heldPipe.getEndpoint(0) : heldPipe.getEndpoint(1);
                    if (connectedElement instanceof Pump) {
                        var pumpView = (PumpView) ViewRepository.getViewOfObject(connectedElement);
                        //pumpView.setInputPipePos(); TODO
                    }
                }
            }
        }
        return success;
    }
}
