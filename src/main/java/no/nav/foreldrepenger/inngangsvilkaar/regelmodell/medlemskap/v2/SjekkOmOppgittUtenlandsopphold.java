package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import no.nav.fpsak.nare.doc.RuleDocumentation;
import no.nav.fpsak.nare.evaluation.Evaluation;
import no.nav.fpsak.nare.specification.LeafSpecification;

@RuleDocumentation(SjekkOmOppgittUtenlandsopphold.ID)
class SjekkOmOppgittUtenlandsopphold extends LeafSpecification<MedlemskapsvilkårMellomregning> {

    static final String ID = "FP_VK_2-1";
    private static final String BESKRIVELSE = "Har bruker utenlandsadresse?";

    SjekkOmOppgittUtenlandsopphold() {
        super(ID, BESKRIVELSE);
    }

    @Override
    public Evaluation evaluate(MedlemskapsvilkårMellomregning mellomregning) {
        if (!mellomregning.grunnlag().søknad().utenlandsopphold().isEmpty()) {
            mellomregning.addAvvik(MedlemskapAvvik.BOSATT_UTENLANDSOPPHOLD);
        }
        return ja();
    }

}
