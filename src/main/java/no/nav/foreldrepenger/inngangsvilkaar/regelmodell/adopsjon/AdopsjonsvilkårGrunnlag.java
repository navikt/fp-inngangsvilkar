package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.adopsjon;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAlias;

import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.RegelKjønn;
import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.VilkårGrunnlag;
import no.nav.fpsak.nare.doc.RuleDocumentationGrunnlag;

@RuleDocumentationGrunnlag
public record AdopsjonsvilkårGrunnlag (List<BekreftetAdopsjonBarn> bekreftetAdopsjonBarn,
                                       boolean ektefellesBarn,
                                       @JsonAlias("soekersKjonn") RegelKjønn søkersKjønn,
                                       boolean mannAdoptererAlene,
                                       LocalDate omsorgsovertakelsesdato,
                                       boolean erStønadsperiodeBruktOpp) implements VilkårGrunnlag {
    public AdopsjonsvilkårGrunnlag {
        Objects.requireNonNull(bekreftetAdopsjonBarn);
    }

    /*
     * erStønadsperiodeBruktOpp er legacy - har slått til i to tilfelle
     */

}
