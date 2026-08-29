package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.fødsel;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;

import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.RegelKjønn;
import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.RegelSøkerRolle;
import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.VilkårGrunnlag;
import no.nav.fpsak.nare.doc.RuleDocumentationGrunnlag;

@RuleDocumentationGrunnlag
public record FødselsvilkårGrunnlag(@JsonAlias("soekersKjonn") RegelKjønn søkersKjønn,
                                    @JsonAlias("soekerRolle") RegelSøkerRolle søkerRolle,
                                    @JsonAlias("dagensdato") LocalDate behandlingsdato,
                                    @JsonAlias("bekreftetFoedselsdato") LocalDate bekreftetFødselsdato,
                                    @JsonAlias("bekreftetTermindato") LocalDate terminbekreftelseTermindato,
                                    int antallBarn,
                                    boolean erFødselRegistreringFristUtløpt,
                                    boolean erMorForSykVedFødsel, // Legacy - tilfelle før WLB
                                    boolean erSøktOmTermin,
                                    boolean erBehandlingsdatoEtterTidligsteDato,
                                    @JsonAlias("erTerminBekreftelseUtstedtEtterXUker") boolean erTerminbekreftelseUtstedtEtterTidligsteDato,
                                    boolean farMedmorUttakRundtFødsel) implements VilkårGrunnlag {
}
