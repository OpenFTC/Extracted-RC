package com.qualcomm.hardware.limelightvision;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a field map containing fiducial markers / AprilTags.
 */
public class LLFieldMap {

    /**
     * Represents a fiducial marker / AprilTag in the field map.
     */
    public static class Fiducial {
        private int id;
        private double size;
        private String family;
        private List<Double> transform;
        private boolean isUnique;

        /**
         * Constructs a Fiducial with default values.
         */
        public Fiducial() {
            this(-1, 165.1, "apriltag3_36h11_classic", new ArrayList<>(16), true);
        }

        /**
         * Constructs a Fiducial with specified values.
         *
         * @param id The ID of the fiducial.
         * @param size The size of the fiducial.
         * @param family The family of the fiducial.
         * @param transform The transformation of the fiducial.
         * @param isUnique Whether the fiducial is unique.
         */
        public Fiducial(int id, double size, String family, List<Double> transform, boolean isUnique) {
            this.id = id;
            this.size = size;
            this.family = family;
            this.transform = new ArrayList<>(transform);
            this.isUnique = isUnique;
        }

        /**
         * Constructs a Fiducial from JSON data. Returns a default Fidcial if JSON is null or malformed
         *
         * @param json The JSON object containing fiducial data.
         */
        protected Fiducial(JSONObject json) {
            this(); //empy Fiducial
            if(json != null)
            {
                try{
                    this.id = json.optInt("id", -1);
                    this.size = json.optDouble("size", 165.1);
                    this.family = json.optString("family", "apriltag3_36h11_classic");
                    this.transform = new ArrayList<>();
                    JSONArray transformArray = json.optJSONArray("transform");
                    if (transformArray != null) {
                        for (int i = 0; i < transformArray.length(); i++) {
                            this.transform.add(transformArray.optDouble(i, 0.0));
                        }
                    }
                    this.isUnique = json.optBoolean("unique", true);
                } catch (Exception e) {

                }
            }
        }

        /**
         * Gets the ID / index of the fiducial.
         *
         * @return The fiducial ID.
         */
        public int getId() {
            return id;
        }

        /**
         * Gets the size of the fiducial in millimeters
         *
         * @return The fiducial size.
         */
        public double getSize() {
            return size;
        }

        /**
         * Gets the family of the fiducial. eg "apriltag3_36h11_classic"
         *
         * @return The fiducial family.
         */
        public String getFamily() {
            return family;
        }

        /**
         * Gets the 4x4 transforms matrix of the fiducial.
         *
         * @return A copy of the fiducial's transform.
         */
        public List<Double> getTransform() {
            return new ArrayList<>(transform);
        }

        /**
         * Checks if the fiducial is marked as unique.
         *
         * @return true if the fiducial is unique, false otherwise.
         */
        public boolean isUnique() {
            return isUnique;
        }

        /**
         * Converts the Fiducial to a JSONObject.
         *
         * @return A JSONObject representation of the Fiducial.
         */
        protected JSONObject toJson() {
            JSONObject json = new JSONObject();
            try {
                json.put("id", id);
                json.put("size", size);
                json.put("family", family);
                json.put("transform", new JSONArray(transform));
                json.put("unique", isUnique);
            } catch (Exception e){

            }
            return json;
        }
    }

    private List<Fiducial> fiducials;
    private String type;

    /**
     * Constructs an empty LLFieldMap.
     */
    public LLFieldMap() {
        this(new ArrayList<>(), "");
    }

    /**
     * Constructs an LLFieldMap with specified fiducials and type.
     *
     * @param fiducials The list of fiducials in the field map.
     * @param type The type of the field map.
     */
    public LLFieldMap(List<Fiducial> fiducials, String type) {
        this.fiducials = new ArrayList<>(fiducials);
        this.type = type;
    }

    /**
     * Constructs an LLFieldMap from JSON data. Returns an empty map if json is null or malformed
     *
     * @param json The JSON object containing field map data.
     */
    protected LLFieldMap(JSONObject json) {
        this();
        if (json != null) {
            try{
                this.type = json.optString("type", "");
                JSONArray fiducialsArray = json.optJSONArray("fiducials");
                if (fiducialsArray != null) {
                    for (int i = 0; i < fiducialsArray.length(); i++) {
                        JSONObject fiducialJson = fiducialsArray.optJSONObject(i);
                        if (fiducialJson != null) {
                            this.fiducials.add(new Fiducial(fiducialJson));
                        }
                    }
                }
            }
            catch (Exception e){
            }
        }
    }

    /**
     * Gets the list of fiducials in the field map.
     *
     * @return A copy of the list of fiducials.
     */
    public List<Fiducial> getFiducials() {
        return new ArrayList<>(fiducials);
    }

    /**
     * Gets the type of the field map. (eg "ftc" or "frc")
     *
     * @return The field map type.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the number of tags (fiducials) in the field map.
     *
     * @return The number of tags.
     */
    public int getNumberOfTags() {
        return fiducials.size();
    }

    /**
     * Get validity of map. Maps are valid if they have more than zero tags and have a specified type
     *
     * @return True if valid. False otherwise
     */
    public boolean isValid()
    {
        if(getNumberOfTags() == 0)
        {
            //Likely user error.
            return false;
        }
        if((getType() != "ftc") && (getType() != "frc"))
        {
            //Likely user error.
            return false;
        }
        return true;
    }

    /**
     * Converts the LLFieldMap to a JSONObject.
     *
     * @return A JSONObject representation of the LLFieldMap.
     */
    protected JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            JSONArray fiducialsArray = new JSONArray();
            for (Fiducial fiducial : fiducials) {
                fiducialsArray.put(fiducial.toJson());
            }
            json.put("fiducials", fiducialsArray);
            json.put("type", type);
        } catch (Exception e) {
        }
        return json;
    }
}