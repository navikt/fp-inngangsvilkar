package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.medlemskap.v1;

import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.VilkårGrunnlag;
import no.nav.fpsak.nare.doc.RuleDocumentationGrunnlag;

/**
 * 1. okt. 2024 - Gammelt regelgrunnlag medlemskapsvilkåret for ev. deserialisering av gammel regelinput
 */
@RuleDocumentationGrunnlag
public record MedlemskapsvilkårGrunnlagV1(RegelPersonStatusType personStatusType, boolean brukerNorskNordisk, boolean brukerBorgerAvEUEOS,
                                          boolean brukerHarOppholdstillatelse, boolean harSøkerArbeidsforholdOgInntekt, boolean brukerErMedlem,
                                          boolean brukerAvklartPliktigEllerFrivillig, boolean brukerAvklartBosatt, boolean brukerAvklartLovligOppholdINorge,
                                          boolean brukerAvklartOppholdsrett) implements VilkårGrunnlag {

    /*
     * Hvis du tenker på annen navngivning - obs på deserialisering av tidligere/nye navn i VedtakXML+ DvhXML .
     * Anbefaler å gjennomgå all vilkårlagring og avklare med Stønadsstatistikk hva som brukes av DvhXML,
     * deretter evt regenerere alle tidliger vedtak. Prosess 2022.
     */

    public enum RegelPersonStatusType {
        BOSA("Bosatt"),
        UTVA("Utvandret"),
        DØD("Død");

        private String navn;

        RegelPersonStatusType(String navn) {
            this.navn = navn;
        }

        public String getNavn() {
            return navn;
        }
    }

}
