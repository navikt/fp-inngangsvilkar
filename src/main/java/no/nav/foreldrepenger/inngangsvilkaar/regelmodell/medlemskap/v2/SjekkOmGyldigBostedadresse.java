package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import no.nav.fpsak.nare.doc.RuleDocumentation;
import no.nav.fpsak.nare.evaluation.Evaluation;
import no.nav.fpsak.nare.specification.LeafSpecification;

@RuleDocumentation(SjekkOmGyldigBostedadresse.ID)
class SjekkOmGyldigBostedadresse extends LeafSpecification<MedlemskapsvilkårMellomregning> {

    static final String ID = "FP_VK_2-4";

    private static final String BESKRIVELSE = "Har bruker gyldig bostedsadresse?";

    SjekkOmGyldigBostedadresse() {
        super(ID, BESKRIVELSE);
    }

    @Override
    public Evaluation evaluate(MedlemskapsvilkårMellomregning mellomregning) {
        var grunnlag = mellomregning.grunnlag();
        var personopplysninger = grunnlag.personopplysninger();
        var vurderingsperiode = grunnlag.vurderingsperiodeBosatt();

        if (MedlemFellesRegler.sjekkOmManglendeBosted(personopplysninger, vurderingsperiode)) {
            mellomregning.addAvvik(MedlemskapAvvik.BOSATT_MANGLENDE_BOSTEDSADRESSE);
        }
        return ja();
    }

}
