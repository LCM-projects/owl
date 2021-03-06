// code by jph
package ch.ethz.idsc.owl.bot.se2.twd;

import ch.ethz.idsc.owl.bot.r2.R2NoiseRegion;
import ch.ethz.idsc.owl.bot.util.DemoInterface;
import ch.ethz.idsc.owl.bot.util.RegionRenders;
import ch.ethz.idsc.owl.glc.adapter.SimpleTrajectoryRegionQuery;
import ch.ethz.idsc.owl.gui.win.OwlyAnimationFrame;
import ch.ethz.idsc.owl.math.region.Region;
import ch.ethz.idsc.owl.math.state.StateTime;
import ch.ethz.idsc.owl.math.state.TrajectoryRegionQuery;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

public class TwdNoiseDemo implements DemoInterface {
  @Override
  public OwlyAnimationFrame start() {
    OwlyAnimationFrame owlyAnimationFrame = new OwlyAnimationFrame();
    owlyAnimationFrame.set(TwdEntity.createDuckie(new StateTime(Tensors.vector(0, 0, 0), RealScalar.ZERO)));
    Region<Tensor> region = new R2NoiseRegion(RealScalar.of(0.1));
    TrajectoryRegionQuery trajectoryRegionQuery = SimpleTrajectoryRegionQuery.timeInvariant(region);
    owlyAnimationFrame.setObstacleQuery(trajectoryRegionQuery);
    owlyAnimationFrame.addBackground(RegionRenders.create(trajectoryRegionQuery));
    return owlyAnimationFrame;
  }

  public static void main(String[] args) {
    new TwdNoiseDemo().start().jFrame.setVisible(true);
  }
}
