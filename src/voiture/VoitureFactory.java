package voiture;
import circuit.Circuit;
public class VoitureFactory {
    private double[] tabVitesse     = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.};
    private double[] tabTurn        = {1.,  0.9, 0.75, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.05};
    private double vmax = 0.9;
    private double alpha_c = 0.005;
    private double braquage = 0.1;
    private double alpha_f = 0.0002;
    private double beta_f = 0.0005;
    private double vitesse = 0; // vitesse initiale
    
    VoitureImpl v;
    
    public VoitureFactory(Circuit circ){
        v = new VoitureImpl(this.tabVitesse, this.tabTurn, this.vmax, this.alpha_c,
            this.braquage, this.alpha_f, this.beta_f, this.vitesse, circ.getPointDepart(), circ.getDirectionDepart());
    }
    
    public Voiture Build() {
        return new VoitureImpl(v);
    }
    
}