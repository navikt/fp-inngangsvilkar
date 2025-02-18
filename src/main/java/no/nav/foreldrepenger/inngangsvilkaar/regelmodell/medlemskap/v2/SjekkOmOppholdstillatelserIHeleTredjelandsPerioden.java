package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import no.nav.fpsak.nare.doc.RuleDocumentation;
import no.nav.fpsak.nare.evaluation.Evaluation;
import no.nav.fpsak.nare.specification.LeafSpecification;

@RuleDocumentation(SjekkOmOppholdstillatelserIHeleTredjelandsPerioden.ID)
class SjekkOmOppholdstillatelserIHeleTredjelandsPerioden extends LeafSpecification<MedlemskapsvilkårMellomregning> {

    static final String ID = "FP_VK_2-6";

    static final String BESKRIVELSE = "Har bruker oppholdstillatelser for perioder med tredjeland?";

    SjekkOmOppholdstillatelserIHeleTredjelandsPerioden() {
        super(ID, BESKRIVELSE);
    }

    @Override
    public Evaluation evaluate(MedlemskapsvilkårMellomregning mellomregning) {
        var grunnlag = mellomregning.grunnlag();
        var personopplysninger = grunnlag.personopplysninger();
        var vurderingsperiode = grunnlag.vurderingsperiodeLovligOpphold();
        if (!MedlemFellesRegler.sjekkOmOppholdstillatelserIHelePeriodenMedTredjelandsRegion(vurderingsperiode, personopplysninger)) {
            mellomregning.addAvvik(MedlemskapAvvik.TREDJELAND_MANGLENDE_LOVLIG_OPPHOLD);
        }

        return ja();
    }
}
