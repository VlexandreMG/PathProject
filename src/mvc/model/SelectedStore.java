package mvc.model;

/**
 * Petit magasin global pour sauvegarder un objet sélectionné dans l'application.
 * Utilisé par les composants UI pour "save" un objet et le rendre accessible
 * ailleurs dans le programme.
 */
public final class SelectedStore {

    private static Object selected;

    private SelectedStore() { }

    public static void setSelected(Object obj) {
        selected = obj;
    }

    public static Object getSelected() {
        return selected;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSelectedAs(Class<T> cls) {
        if (selected == null) return null;
        if (cls.isInstance(selected)) {
            return (T) selected;
        }
        return null;
    }
}
