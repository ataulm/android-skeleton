package com.example

internal class Presenter {

    private val selectedEmailIds: MutableSet<String> = mutableSetOf()

    fun startPresenting(view: View) {
        val emailUiModels = fakeEmails.map {
            val contactImageUrl = "https://robohash.org/${it.contact}?bgset=bg1"
            val isSelected: () -> Boolean = { selectedEmailIds.contains(it.id) }
            val setSelected: (Boolean) -> Unit = { setSelected ->
                if (setSelected) {
                    selectedEmailIds += it.id
                } else {
                    selectedEmailIds -= it.id
                }
            }
            val onClick: () -> Unit = { view.openEmail(it.id) }
            EmailUiModel(it.id, contactImageUrl, it.preview, isSelected, setSelected, onClick)
        }
        view.display(emailUiModels)
    }

    private val fakeEmails = listOf(
            Email("1", "alice@example.com", "Zombie ipsum reversus ab viral inferno, nam rick grimes malum cerebro."),
            Email("2", "bob@example.com", "De carne lumbering animata corpora quaeritis."),
            Email("3", "carol@example.com", "Summus brains sit, morbo vel maleficia?"),
            Email("4", "dan@example.com", "De apocalypsi gorger omero undead survivor dictum mauris"),
            Email("5", "erin@example.com", "Hi mindless mortuis soulless creaturas"),
            Email("6", "faythe@example.com", "imo evil stalking monstra adventus resi dentevil"),
            Email("7", "grace@example.com", "vultus comedat cerebella viventium"),
            Email("8", "heidi@example.com", "Qui animated corpse, cricket bat max brucks"),
            Email("9", "judy@example.com", "Zonbi tattered for solum oculi eorum defunctis go lum cerebro")
    )

    interface View {

        fun display(emails: List<EmailUiModel>)
        fun openEmail(emailId: String)
    }
}
