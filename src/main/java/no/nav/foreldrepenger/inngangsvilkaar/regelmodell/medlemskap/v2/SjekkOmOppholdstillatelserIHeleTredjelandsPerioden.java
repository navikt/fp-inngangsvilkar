package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import java.util.Set;

import no.nav.fpsak.nare.doc.RuleDocumentation;
import no.nav.fpsak.nare.evaluation.Evaluation;
import no.nav.fpsak.nare.specification.LeafSpecification;
import no.nav.fpsak.tidsserie.LocalDateInterval;
import no.nav.fpsak.tidsserie.LocalDateSegment;
import no.nav.fpsak.tidsserie.LocalDateTimeline;

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

    private static LocalDateTimeline<Personopplysninger.Region> regionTimelineIVurderingsperiode(Set<Personopplysninger.RegionPeriode> regioner,
                                                                                                 LocalDateInterval vurderingsperiode) {
        var regionSegmenter = regioner.stream()
            .filter(srp -> srp.region().equals(Personopplysninger.Region.TREDJELAND))
            .map(rp -> new LocalDateSegment<>(rp.periode(), rp.region()))
            .toList();

        return new LocalDateTimeline<>(regionSegmenter).intersection(vurderingsperiode);
    }
}
