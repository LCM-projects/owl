// code by jph
package ch.ethz.idsc.owl.bot.rn.rrts;

import ch.ethz.idsc.owl.bot.r2.R2NoiseRegion;
import ch.ethz.idsc.owl.glc.adapter.SimpleTrajectoryRegionQuery;
import ch.ethz.idsc.owl.math.region.PolygonRegion;
import ch.ethz.idsc.owl.rrts.adapter.SampledTransitionRegionQuery;
import ch.ethz.idsc.owl.rrts.core.TransitionRegionQuery;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;

/* package */ enum StaticHelper {
  ;
  public static TransitionRegionQuery polygon1() {
    return new SampledTransitionRegionQuery(SimpleTrajectoryRegionQuery.timeInvariant( //
        PolygonRegion.of(Tensors.matrix(new Number[][] { //
            { 3, 1 }, //
            { 4, 1 }, //
            { 4, 6 }, //
            { 1, 6 }, //
            { 1, 3 }, //
            { 3, 3 } //
        }))), RealScalar.of(0.1));
  }

  public static TransitionRegionQuery noise1() {
    return new SampledTransitionRegionQuery(SimpleTrajectoryRegionQuery.timeInvariant( //
        new R2NoiseRegion(RealScalar.of(0.4))), RealScalar.of(0.1));
  }
}
