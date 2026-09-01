package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.opptjening;

import java.time.Period;

import no.nav.fpsak.tidsserie.LocalDateTimeline;

/**
 * Beskriver opptjente dager og totalt beregnet periode opptjent.
 * Opptjent periode innenfor tidslinjen. Kan avvike noe fra dager i tidslinjen pga. spesielle regler rundt telling av måneder
 *  (eks. regel om måneder og 26 opptjente dager = 6 oppjente måneder).
 * Tidslinje med opptjente dager. Dager som ikke er opptjent er satt til false.
 */
 public record OpptjentTidslinje(Period opptjentPeriode, LocalDateTimeline<Boolean> tidslinje) {
}
