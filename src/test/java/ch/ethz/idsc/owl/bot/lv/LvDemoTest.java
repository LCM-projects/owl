// code by jph
package ch.ethz.idsc.owl.bot.lv;

import java.util.Collection;

import ch.ethz.idsc.owl.glc.adapter.Expand;
import ch.ethz.idsc.owl.glc.core.GoalInterface;
import ch.ethz.idsc.owl.glc.core.TrajectoryPlanner;
import ch.ethz.idsc.owl.glc.std.StandardTrajectoryPlanner;
import ch.ethz.idsc.owl.math.StateSpaceModel;
import ch.ethz.idsc.owl.math.StateTimeTensorFunction;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.owl.math.flow.RungeKutta45Integrator;
import ch.ethz.idsc.owl.math.region.EllipsoidRegion;
import ch.ethz.idsc.owl.math.state.EmptyTrajectoryRegionQuery;
import ch.ethz.idsc.owl.math.state.FixedStateIntegrator;
import ch.ethz.idsc.owl.math.state.StateIntegrator;
import ch.ethz.idsc.owl.math.state.StateTime;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Log;
import junit.framework.TestCase;

public class LvDemoTest extends TestCase {
  public void testSimple() {
    for (int index = 0; index < 5; ++index) {
      Tensor eta = Tensors.vector(10, 10);
      StateIntegrator stateIntegrator = FixedStateIntegrator.create( //
          RungeKutta45Integrator.INSTANCE, RationalScalar.of(1, 30), 4);
      StateSpaceModel stateSpaceModel = LvStateSpaceModel.of(1, 2);
      Collection<Flow> controls = LvControls.create(stateSpaceModel, 2);
      EllipsoidRegion ellipsoidRegion = new EllipsoidRegion(Tensors.vector(2, 1), Tensors.vector(0.1, 0.1));
      GoalInterface goalInterface = new LvGoalInterface(ellipsoidRegion);
      // ---
      TrajectoryPlanner trajectoryPlanner = new StandardTrajectoryPlanner( //
          eta, stateIntegrator, controls, EmptyTrajectoryRegionQuery.INSTANCE, goalInterface);
      // ---
      trajectoryPlanner.represent = StateTimeTensorFunction.state(Log::of);
      trajectoryPlanner.insertRoot(new StateTime(Tensors.vector(2, 0.3), RealScalar.ZERO));
      int steps = Expand.maxSteps(trajectoryPlanner, 10000);
      if (steps < 9800)
        return;
      System.out.println("lv steps=" + steps);
    }
    assertTrue(false);
  }
}
