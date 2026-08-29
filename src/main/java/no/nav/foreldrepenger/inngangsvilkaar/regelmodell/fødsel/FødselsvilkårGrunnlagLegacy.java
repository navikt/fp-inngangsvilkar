package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.fødsel;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;

import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.LegacyLocalDateDeserializer;
import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.RegelKjønn;
import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.RegelSøkerRolle;
import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.VilkårGrunnlag;
import no.nav.fpsak.nare.doc.RuleDocumentationGrunnlag;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * Denne finnes utelukkende pga VedtakXML og DvhSML - deserialisere gamle ES-vilkår som har dato som objekt (se FødselsVilkårDocTest for exempel)
 */
@RuleDocumentationGrunnlag
public record FødselsvilkårGrunnlagLegacy(@JsonAlias("soekersKjonn") RegelKjønn søkersKjønn,
                                          @JsonAlias("soekerRolle") RegelSøkerRolle søkerRolle,
                                          @JsonDeserialize(using = LegacyLocalDateDeserializer.class) @JsonAlias({"dagensdato","soeknadsdato"}) LocalDate behandlingsdato,
                                          @JsonDeserialize(using = LegacyLocalDateDeserializer.class) @JsonAlias("bekreftetFoedselsdato") LocalDate bekreftetFødselsdato,
                                          @JsonDeserialize(using = LegacyLocalDateDeserializer.class) @JsonAlias("bekreftetTermindato") LocalDate terminbekreftelseTermindato,
                                          int antallBarn,
                                          boolean erFødselRegistreringFristUtløpt,
                                          boolean erMorForSykVedFødsel,
                                          boolean erSøktOmTermin,
                                          boolean erBehandlingsdatoEtterTidligsteDato,
                                          @JsonAlias("erTerminBekreftelseUtstedtEtterXUker") boolean erTerminbekreftelseUtstedtEtterTidligsteDato,
                                          boolean farMedmorUttakRundtFødsel) implements VilkårGrunnlag {
}
