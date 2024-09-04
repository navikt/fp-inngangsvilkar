package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import no.nav.fpsak.nare.doc.RuleDocumentation;
import no.nav.fpsak.nare.evaluation.Evaluation;
import no.nav.fpsak.nare.specification.LeafSpecification;

@RuleDocumentation(SjekkOmUtenlandsadresser.ID)
class SjekkOmUtenlandsadresser extends LeafSpecification<MedlemskapsvilkårMellomregning> {

    static final String ID = "FP_VK_2-7";
    private static final String BESKRIVELSE = "Har bruker utenlandsadresse?";

    SjekkOmUtenlandsadresser() {
        super(ID, BESKRIVELSE);
    }

    @Override
    public Evaluation evaluate(MedlemskapsvilkårMellomregning mellomregning) {
        var grunnlag = mellomregning.grunnlag();
        var personopplysninger = grunnlag.personopplysninger();
        var vurderingsperiode = grunnlag.vurderingsperiodeBosatt();
        if (MedlemFellesRegler.sjekkOmUtenlandsadresser(personopplysninger, vurderingsperiode)) {
            mellomregning.addAvvik(MedlemskapAvvik.BOSATT_UTENLANDSADRESSE);
        }
        return ja();
    }
}
