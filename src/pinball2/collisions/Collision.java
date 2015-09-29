package pinball2.collisions;

import pinball2.Vector;
import pinball2.solids.Solid;
import pinball2.solids.dynamics.DynamicSolid;

public class Collision {
  public final DynamicSolid solidA;
  public final Solid solidB;
  public final double dTimeS;
  public final Vector normal; // collision normal that is always from solidA side of the collision plane to solidB
  public final Vector solidADVel;
  public final Vector solidBDVel;
  
  public Collision(DynamicSolid solidA, Solid solidB, double dTimeS, Vector normal) {
    this.solidA = solidA;
    this.solidB = solidB;
    this.dTimeS = dTimeS;
    this.normal = normal;
    
    if (solidB instanceof DynamicSolid) {
      DynamicSolid _solidA = solidA;
      DynamicSolid _solidB = (DynamicSolid)solidB;
      
      // project the velocities along the collision normal
      double v_ai = _solidA.vel.dot(normal);
      double v_bi = _solidB.vel.dot(normal);
      
      // conservation of momentum states
      // p_i = p_f
      // so in our system of solids a and b
      // p_ai + p_bi = p_af + p_bf
      // momentum is given by
      // p = m*v
      // m_a*v_ai + m_b*v_bi = m_a*v_af + m_b*v_bf
      
      // conservation of kenetic energy states
      // E_ki = E_kf
      // so in our system of solids a and b
      // E_kai = E_kbi
      // kenetic energy of a mass in motion is given by
      // E_k = 1/2*m*v^2
      // 1/2*m_a*v_ai^2 + 1/2*m_b*v_bi^2 = 1/2*m_a*v_af^2 + 1/2*m_b*v_bf^2
      
      // using these two formulas and some algebra we find that
      // v_af = (v_ai*(m_a - m_b) + 2*m_b*v_bi)/(m_a + m_b)
      // v_bf = (v_bi*(m_b - m_a) + 2*m_a*v_ai)/(m_a + m_b)
      // or
      // v_bf = (m_a*v_ai + m_b*v_bi - m_a*v_af)/m_b
      // TODO:
      double v_af = v_bi;
      double v_bf = v_ai;
      
      // apply COR
      v_af *= _solidA.surfaceProperties.cor * _solidB.surfaceProperties.cor;
      v_bf *= _solidA.surfaceProperties.cor * _solidB.surfaceProperties.cor;
      
      // v_f = v_i + Δv
      // Δv = v_f - f_i
      double dv_a = v_af - v_ai;
      double dv_b = v_bf - v_bi;
      
      solidADVel = normal.scale(dv_a);
      solidBDVel = normal.scale(dv_b);
    }
    else {
      // project the velocities along the collision normal
      double v_ai = solidA.vel.dot(normal);
      
      // the other solid is static (infinite mass) so the velocity simply switches directions
      double v_af = -v_ai;
      
      // apply COR
      v_af *= solidA.surfaceProperties.cor * solidB.surfaceProperties.cor;
      
      // v_f = v_i + Δv
      double dv_a = v_af - v_ai;
      
      solidADVel = normal.scale(dv_a);
      solidBDVel = null;
    }
  }
}
