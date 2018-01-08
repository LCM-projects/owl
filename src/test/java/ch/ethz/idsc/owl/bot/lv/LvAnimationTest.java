// code by jph
package ch.ethz.idsc.owl.bot.lv;

import java.util.Collection;

import ch.ethz.idsc.owl.gui.ren.VectorFieldRender;
import ch.ethz.idsc.owl.math.StateSpaceModel;
import ch.ethz.idsc.owl.math.VectorFields;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.owl.math.sample.BoxRandomSample;
import ch.ethz.idsc.owl.math.sample.RandomSample;
import ch.ethz.idsc.owl.math.sample.RandomSampleInterface;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class LvAnimationTest extends TestCase {
  public void testSimple() {
    StateSpaceModel stateSpaceModel = LvStateSpaceModel.of(1, 2);
    Collection<Flow> controls = LvControls.create(stateSpaceModel, 2);
    new LvEntity(stateSpaceModel, Tensors.vector(2, 0.3), controls);
    Tensor range = Tensors.vector(6, 5);
    VectorFieldRender vectorFieldRender = new VectorFieldRender();
    RandomSampleInterface sampler = new BoxRandomSample(Tensors.vector(0, 0), range);
    Tensor points = Tensor.of(RandomSample.of(sampler, 1000).stream());
    vectorFieldRender.uv_pairs = //
        VectorFields.of(stateSpaceModel, points, LvEntity.FALLBACK_CONTROL, RealScalar.of(0.04));
  }
}