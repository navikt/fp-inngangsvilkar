package no.nav.foreldrepenger.inngangsvilkaar.regelmodell;

import no.nav.fpsak.nare.evaluation.summary.EvaluationVersion;
import no.nav.fpsak.nare.evaluation.summary.NareVersion;

public class InngangsvilkårVersjon {

    private InngangsvilkårVersjon() {
    }

    public static final EvaluationVersion INNGANGSVILKÅR_VERSJON = NareVersion.readVersionPropertyFor("fp-inngangsvilkar", "nare/fp-inngangsvilkar-version.properties");


}
