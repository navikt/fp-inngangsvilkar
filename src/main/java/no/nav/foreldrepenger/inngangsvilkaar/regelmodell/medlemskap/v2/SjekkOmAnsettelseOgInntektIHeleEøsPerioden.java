package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2;

import static no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v2.MedlemInngangsvilkårRegelGrunnlag.Beløp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.stream.Collectors;

import no.nav.fpsak.nare.doc.RuleDocumentation;
import no.nav.fpsak.nare.evaluation.Evaluation;
import no.nav.fpsak.nare.specification.LeafSpecification;
import no.nav.fpsak.tidsserie.LocalDateInterval;
import no.nav.fpsak.tidsserie.LocalDateSegment;
import no.nav.fpsak.tidsserie.LocalDateTimeline;

@RuleDocumentation(SjekkOmAnsettelseOgInntektIHeleEøsPerioden.ID)
class SjekkOmAnsettelseOgInntektIHeleEøsPerioden extends LeafSpecification<MedlemskapsvilkårMellomregning> {

    static final String ID = "FP_VK_2-2";

    static final String BESKRIVELSE = "Har bruker ansettelse i hele perioden med eøs?";

    SjekkOmAnsettelseOgInntektIHeleEøsPerioden() {
        super(ID, BESKRIVELSE);
    }

    @Override
    public Evaluation evaluate(MedlemskapsvilkårMellomregning mellomregning) {
        var grunnlag = mellomregning.grunnlag();
        var personopplysninger = grunnlag.personopplysninger();
        var vurderingsperiode = grunnlag.vurderingsperiodeLovligOpphold();
        var regionTimeline = regionTimelineIVurderingsperiode(personopplysninger.regioner(), vurderingsperiode);
        if (!regionTimeline.isEmpty()) {
            var ansettelsePerioder = grunnlag.arbeid().ansettelsePerioder();
            if (!MedlemFellesRegler.sjekkOmAnsettelseIHelePeriodenMedEøsRegion(ansettelsePerioder, personopplysninger, vurderingsperiode)
                || !harInntektSiste3mndFørStp(grunnlag)) {
                mellomregning.addAvvik(MedlemskapAvvik.EØS_MANGLENDE_ANSETTELSE_MED_INNTEKT);
            }
        }
        return ja();
    }

    private static boolean harInntektSiste3mndFørStp(MedlemInngangsvilkårRegelGrunnlag grunnlag) {
        var inntekter = grunnlag.arbeid().inntekter();
        Set<LocalDateSegment<Beløp>> inntektSegmenter = inntekter.stream()
            .map(i -> new LocalDateSegment<>(i.interval(), i.beløp()))
            .collect(Collectors.toSet());
        var stpInntekt = grunnlag.skjæringstidspunkt().isAfter(grunnlag.behandlingsdato()) ? grunnlag.behandlingsdato() : grunnlag.skjæringstidspunkt();
        var relevantInntektsInterval = new LocalDateInterval(stpInntekt.minusMonths(3), stpInntekt.minusDays(1)); //3 hele måneder med inntekt
        var inntektTimeline = new LocalDateTimeline<>(inntektSegmenter, (datoInterval, datoSegment, datoSegment2) -> new LocalDateSegment<>(datoInterval,
            datoSegment.getValue().add(datoSegment2.getValue()))).intersection(relevantInntektsInterval);

        var godkjentSamletInntekt = grunnlag.grunnbeløp().divide(new Beløp(BigDecimal.valueOf(4)), RoundingMode.DOWN);
        return inntektTimeline.stream().map(LocalDateSegment::getValue).reduce(Beløp.ZERO, Beløp::add).erMerEnn(godkjentSamletInntekt);
    }

    private static LocalDateTimeline<Personopplysninger.Region> regionTimelineIVurderingsperiode(Set<Personopplysninger.RegionPeriode> regioner,
                                                                                                 LocalDateInterval vurderingsperiode) {
        var regionSegmenter = regioner.stream()
            .filter(srp -> srp.region().equals(Personopplysninger.Region.EØS))
            .map(rp -> new LocalDateSegment<>(rp.periode(), rp.region()))
            .toList();

        return new LocalDateTimeline<>(regionSegmenter).intersection(vurderingsperiode);
    }

}
