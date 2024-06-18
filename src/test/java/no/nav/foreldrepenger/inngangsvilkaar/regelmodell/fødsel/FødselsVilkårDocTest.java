package no.nav.foreldrepenger.inngangsvilkaar.regelmodell.fødsel;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.RegelKjønn;
import no.nav.foreldrepenger.inngangsvilkaar.regelmodell.RegelSøkerRolle;
import no.nav.fpsak.nare.json.JsonOutput;

class FødselsVilkårDocTest {

    private static ObjectMapper createObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.registerModule(new Jdk8Module());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        om.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        om.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        om.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
        return om;
    }


    private static final String gammelJson = """
        {
            "soekersKjonn" : "KVINNE",
            "bekreftetFoedselsdato" : null,
            "antallBarn" : 1,
            "bekreftetTermindato" : "2021-05-20",
            "soekerRolle" : null,
            "dagensdato" : "2021-04-22",
            "erMorForSykVedFødsel" : false,
            "erSøktOmTermin" : true,
            "erTerminBekreftelseUtstedtEtterXUker" : true
        }
        """;

    @Test
    void kanDeserialisereGammeltFormat() throws JsonProcessingException {
        var gsource = new FødselsvilkårGrunnlag(RegelKjønn.KVINNE, null, LocalDate.of(2021,4,22),
            null, LocalDate.of(2021,5,20), 1,
            false, false, true,
            false, true, false);
        var grunnlag = deserialiser(gammelJson);
        assertThat(grunnlag).isEqualTo(gsource);
    }

    @Test
    void kanSerialisereDeserialisereNyttFormat() throws JsonProcessingException {
        var gsource = new FødselsvilkårGrunnlag(RegelKjønn.MANN, RegelSøkerRolle.FARA, LocalDate.now().minusWeeks(1),
            null, LocalDate.now().plusMonths(1), 1,
            false, false, true,
            true, true, false);
        var serialisert = JsonOutput.asJson(gsource);
        var grunnlag = deserialiser(serialisert);
        assertThat(grunnlag).isEqualTo(gsource);
    }

    private FødselsvilkårGrunnlag deserialiser(String s) throws JsonProcessingException {
        return createObjectMapper().readValue(s, FødselsvilkårGrunnlag.class);
    }

    private static String eldgammelJson = """
        {
          "soekersKjonn" : "KVINNE",
          "bekreftetFoedselsdato" : {
            "year" : 2017,
            "month" : "AUGUST",
            "chronology" : {
              "calendarType" : "iso8601",
              "id" : "ISO"
            },
            "dayOfMonth" : 5,
            "dayOfWeek" : "SATURDAY",
            "era" : "CE",
            "dayOfYear" : 217,
            "leapYear" : false,
            "monthValue" : 8
          },
          "antallBarn" : 1,
          "bekreftetTermindato" : null,
          "soekerRolle" : "MORA",
          "soeknadsdato" : {
            "year" : 2018,
            "month" : "FEBRUARY",
            "chronology" : {
              "calendarType" : "iso8601",
              "id" : "ISO"
            },
            "dayOfMonth" : 5,
            "dayOfWeek" : "MONDAY",
            "era" : "CE",
            "dayOfYear" : 36,
            "leapYear" : false,
            "monthValue" : 2
          }
        }
        """;

    @Test
    void kanSerialisereDeserialisereEldgammeltFormat() throws JsonProcessingException {
        var grunnlag = createObjectMapper().readValue(eldgammelJson, FødselsvilkårGrunnlagLegacy.class);
        assertThat(grunnlag.behandlingsdato()).isEqualTo(LocalDate.of(2018,2,5));
        assertThat(grunnlag.bekreftetFødselsdato()).isEqualTo(LocalDate.of(2017,8,5));
    }

}
