package macaroni.actions;

import macaroni.app.gameView.ViewRepository;
import macaroni.model.character.Character;
import macaroni.model.character.Plumber;
import macaroni.model.effects.BananaEffect;
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
        var moveToBananaPipe = element instanceof Pipe pipe && pipe.getEffect() instanceof BananaEffect && !pipe.isOccupied();
        var locationBeforeMove = actor.getLocation();
        var success = actor.moveTo(element);
        var locationAfterMove = actor.getLocation();

        if (success || moveToBananaPipe) {
            System.out.println("Move success");

            if (locationBeforeMove != locationAfterMove) {
                var characterView = (CharacterView) ViewRepository.getViewOfObject(actor);
                characterView.setPosition(ViewRepository.getViewOfObject(locationAfterMove).getPosition());

                if (actor instanceof Plumber plumber) {
                    var heldPipe = plumber.getHeldPipe();
                    if (heldPipe != null) {
                        var heldPipeView = (PipeView) ViewRepository.getViewOfObject(heldPipe);
                        var endpoint1 = ViewRepository.getViewOfObject(locationBeforeMove).getPosition();
                        var endpoint2 = ViewRepository.getViewOfObject(locationAfterMove).getPosition();

                        heldPipeView.replaceEndpointPos(endpoint1, endpoint2);

                        var effect = heldPipe.getEffect();
                        if (!(effect instanceof NoEffect)) {
                            var v = ViewRepository.getViewOfObject(effect);
                            v.setPosition(heldPipeView.getPosition());
                        }

                        var connectedElement = heldPipe.getEndpoint(0);
                        if (connectedElement instanceof Pump pump) {
                            var pumpView = (PumpView) ViewRepository.getViewOfObject(connectedElement);

                            if (pump.getInputPipe() == heldPipe) pumpView.setInputPipePos(heldPipeView.getPosition());
                            if (pump.getOutputPipe() == heldPipe) pumpView.setOutputPipePos(heldPipeView.getPosition());
                        }
                    }
                }
            }
            return true;
        }
        System.out.println("Move fail");
        return false;
    }
}
