package com.ataulm.basic;

class LondonUndergroundRemoteApi {

    public String getUndergroundLinesJson() {
        simulateDelayFromNetworkCall();
        return RESPONSE;
    }

    private void simulateDelayFromNetworkCall() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {
            // super not bothered
        }
    }

    private static final String RESPONSE = "{\n" +
            "  \"lines\": [\n" +
            "    {\n" +
            "      \"id\": \"B\",\n" +
            "      \"name\": \"Bakerloo\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"C\",\n" +
            "      \"name\": \"Central\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"D\",\n" +
            "      \"name\": \"District\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"H\",\n" +
            "      \"name\": \"Hammersmith & Circle\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"J\",\n" +
            "      \"name\": \"Jubilee\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"M\",\n" +
            "      \"name\": \"Metropolitan\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"N\",\n" +
            "      \"name\": \"Northern\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"P\",\n" +
            "      \"name\": \"Piccadilly\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"V\",\n" +
            "      \"name\": \"Victoria\",\n" +
            "      \"stations\": [\n" +
            "        {\n" +
            "          \"id\": \"BRX\",\n" +
            "          \"name\": \"Brixton\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"STK\",\n" +
            "          \"name\": \"Stockwell\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"VUX\",\n" +
            "          \"name\": \"Vauxhall\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"PIM\",\n" +
            "          \"name\": \"Pimlico\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"VIC\",\n" +
            "          \"name\": \"Victoria\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"GPK\",\n" +
            "          \"name\": \"Green Park\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"OXC\",\n" +
            "          \"name\": \"Oxford Circus\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"WST\",\n" +
            "          \"name\": \"Warren Street\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"EUS\",\n" +
            "          \"name\": \"Euston\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"KXX\",\n" +
            "          \"name\": \"King's Cross St Pancras\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"HBY\",\n" +
            "          \"name\": \"Highbury & Islington\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"FPK\",\n" +
            "          \"name\": \"Finsbury Park\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"SVS\",\n" +
            "          \"name\": \"Seven Sisters\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"THH\",\n" +
            "          \"name\": \"Tottenham Hale\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"BHR\",\n" +
            "          \"name\": \"Blackhorse Road\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"WAL\",\n" +
            "          \"name\": \"Walthamstow Central\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"W\",\n" +
            "      \"name\": \"Waterloo & City\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

}
