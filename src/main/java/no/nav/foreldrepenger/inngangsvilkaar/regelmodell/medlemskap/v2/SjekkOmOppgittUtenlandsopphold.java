package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import java.util.Objects;
import java.util.Set;

import no.nav.fpsak.nare.doc.RuleDocumentation;
import no.nav.fpsak.nare.evaluation.Evaluation;
import no.nav.fpsak.nare.specification.LeafSpecification;
import no.nav.fpsak.tidsserie.LocalDateInterval;

@RuleDocumentation(SjekkOmOppgittUtenlandsopphold.ID)
class SjekkOmOppgittUtenlandsopphold extends LeafSpecification<MedlemskapsvilkårMellomregning> {

    static final String ID = "FP_VK_2-1";
    private static final String BESKRIVELSE = "Har bruker utenlandsadresse?";

    SjekkOmOppgittUtenlandsopphold() {
        super(ID, BESKRIVELSE);
    }

    @Override
    public Evaluation evaluate(MedlemskapsvilkårMellomregning mellomregning) {
        var utenlandsopphold = mellomregning.grunnlag().søknad().utenlandsopphold();
        if (harAvvik(utenlandsopphold, mellomregning.grunnlag().revurderingÅrsak())) {
            mellomregning.addAvvik(MedlemskapAvvik.BOSATT_UTENLANDSOPPHOLD);
        }
        return ja();
    }

    static boolean harAvvik(Set<LocalDateInterval> utenlandsopphold, RevurderingÅrsak revurderingÅrsak) {
        if (utenlandsopphold.isEmpty()) {
            return false;
        }
        return revurderingÅrsak == null || Objects.equals(RevurderingÅrsak.MANUELL, revurderingÅrsak);
    }

}
