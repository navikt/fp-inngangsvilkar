package no.nav.foreldrepenger.inngangsvilkaar.regelmodell;

import java.time.LocalDate;
import java.time.Month;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;

public class LegacyLocalDateDeserializer extends StdDeserializer<LocalDate> {

    public LegacyLocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext ctxt) throws JacksonException {
        JsonNode node = parser.readValueAsTree();
        try {
            var year = node.get("year").intValue();
            var month = Month.valueOf(node.get("month").asString());
            var day = node.get("dayOfMonth").intValue();
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw DatabindException.from(parser, node.toString(), e);
        }
    }
}
