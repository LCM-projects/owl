// code by jph
package ch.ethz.idsc.owl.bot.se2.glc;

import ch.ethz.idsc.owl.bot.r2.R2ImageRegionWrap;
import ch.ethz.idsc.owl.bot.r2.R2ImageRegions;
import ch.ethz.idsc.owl.bot.util.RegionRenders;
import ch.ethz.idsc.owl.glc.adapter.SimpleTrajectoryRegionQuery;
import ch.ethz.idsc.owl.gui.RenderInterface;
import ch.ethz.idsc.owl.gui.win.OwlyAnimationFrame;
import ch.ethz.idsc.owl.math.region.ImageRegion;
import ch.ethz.idsc.owl.math.state.StateTime;
import ch.ethz.idsc.owl.math.state.TrajectoryRegionQuery;
import ch.ethz.idsc.owl.sim.CameraEmulator;
import ch.ethz.idsc.owl.sim.LidarEmulator;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;

public class Se2Letter3Demo extends Se2CarDemo {
  @Override // from Se2CarDemo
  void configure(OwlyAnimationFrame owlyAnimationFrame) {
    R2ImageRegionWrap r2ImageRegionWrap = R2ImageRegions._GTOB;
    CarEntity carEntity = CarEntity.createDefault(new StateTime(Tensors.vector(6, 6, 1), RealScalar.ZERO));
    carEntity.extraCosts.add(r2ImageRegionWrap.costFunction());
    // se2Entity.extraCosts.add(r2ImageRegionWrap.gradientCostFunction());
    ImageRegion imageRegion = r2ImageRegionWrap.imageRegion();
    TrajectoryRegionQuery trq = createCarQuery(imageRegion);
    carEntity.obstacleQuery = trq;
    TrajectoryRegionQuery ray = SimpleTrajectoryRegionQuery.timeInvariant(imageRegion);
    owlyAnimationFrame.set(carEntity);
    owlyAnimationFrame.setObstacleQuery(trq);
    owlyAnimationFrame.addBackground(RegionRenders.create(imageRegion));
    {
      RenderInterface renderInterface = new CameraEmulator( //
          48, RealScalar.of(10), carEntity::getStateTimeNow, ray);
      owlyAnimationFrame.addBackground(renderInterface);
    }
    {
      RenderInterface renderInterface = new LidarEmulator( //
          LidarEmulator.DEFAULT, carEntity::getStateTimeNow, ray);
      owlyAnimationFrame.addBackground(renderInterface);
    }
  }

  public static void main(String[] args) {
    new Se2Letter3Demo().start().jFrame.setVisible(true);
  }
}
