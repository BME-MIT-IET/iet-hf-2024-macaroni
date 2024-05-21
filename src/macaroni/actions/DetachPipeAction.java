package macaroni.actions;

import macaroni.app.gameView.ViewRepository;
import macaroni.model.character.Plumber;
import macaroni.model.element.ActiveElement;
import macaroni.model.element.Pipe;
import macaroni.model.element.Pump;
import macaroni.views.PipeView;
import macaroni.views.PumpView;

/**
 * Cső aktív elemről való lekötési interakciót kezelő osztály.
 */
public class DetachPipeAction extends Action {

    /**
     * A szerelő, aki az akciót végzi
     */
    private final Plumber actor;
    /**
     * Az aktív elem, amiről lecsatlakoztatja a csövet
     */
    private final ActiveElement activeElement;

    /**
     * Létrehoz egy DetachPipeAction példányt.
     *
     * @param actor A szerelő, aki az akciót végzi
     * @param activeElement Az aktív elem, amiről lecsatlakoztatja a csövet
     */
    public DetachPipeAction(Plumber actor, ActiveElement activeElement) {
        this.actor = actor;
        this.activeElement = activeElement;
    }

    /**
     * Ha tudja, elvégzi a paraméterként kapott cső lecsatlakoztatását.
     *
     * @param pipe a cső, amin action-t szeretnénk végezni.
     * @return Igazzal tér vissza ha sikeres.
     */
    @Override
    public boolean doAction(Pipe pipe) {
        var success = actor.detachPipe(activeElement, pipe);
        if (success) {
            System.out.println("Detach pipe success");
            var detachedPipeView = (PipeView) ViewRepository.getViewOfObject(pipe);
            detachedPipeView.replaceEndpointPos(
                    ViewRepository.getViewOfObject(activeElement).getPosition(),
                    ViewRepository.getViewOfObject(actor).getPosition()
            );

            if (activeElement instanceof Pump pump) {
                var pumpView = (PumpView) ViewRepository.getViewOfObject(activeElement);

                if (pump.getInputPipe() == null) pumpView.setInputPipePos(null);
                if (pump.getOutputPipe() == null) pumpView.setOutputPipePos(null);
            }
        }
        return success;
    }
}
