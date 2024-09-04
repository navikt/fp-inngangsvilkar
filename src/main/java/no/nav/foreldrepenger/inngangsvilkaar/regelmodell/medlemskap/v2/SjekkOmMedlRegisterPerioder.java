package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import no.nav.fpsak.nare.doc.RuleDocumentation;
import no.nav.fpsak.nare.evaluation.Evaluation;
import no.nav.fpsak.nare.specification.LeafSpecification;

@RuleDocumentation(SjekkOmMedlRegisterPerioder.ID)
class SjekkOmMedlRegisterPerioder extends LeafSpecification<MedlemskapsvilkårMellomregning> {

    static final String ID = "FP_VK_2-5";
    private static final String BESKRIVELSE = "Har bruker medl perioder?";

    SjekkOmMedlRegisterPerioder() {
        super(ID, BESKRIVELSE);
    }

    @Override
    public Evaluation evaluate(MedlemskapsvilkårMellomregning mellomregning) {
        var grunnlag = mellomregning.grunnlag();

        var vurderingsperiode = grunnlag.vurderingsperiodeBosatt();
        var registerMedlemskapBeslutninger = grunnlag.registrertMedlemskapPerioder();

        if (registerMedlemskapBeslutninger.stream().anyMatch(mp -> mp.overlaps(vurderingsperiode))) {
            mellomregning.addAvvik(MedlemskapAvvik.MEDL_PERIODER);
        }
        return ja();
    }
}
