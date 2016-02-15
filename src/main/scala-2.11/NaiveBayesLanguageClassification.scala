import scala.collection.mutable.HashMap

/**
  * Created by gene on 1/27/16.
  */
object NaiveBayesLanguageClassification {

  var labels = List[String]()
  val wordCounts = new HashMap[String,Double]()
  val wordLabelCounts = new HashMap[String,Double]()
  val docCounts = new HashMap[String, Double]()

  def main(args: Array[String]): Unit = {
    // French Training
    train("L'Italie a été gouvernée pendant un an par un homme qui n'avait pas été élu par le peuple. Dès la nomination de Mario Monti au poste de président du conseil, fin 2011, j'avais dit :Attention, c'est prendre un risque politique majeur. Par leur vote, les Italiens n'ont pas seulement adressé un message à leurs élites nationales, ils ont voulu dire : Nous, le peuple, nous voulons garder la maîtrise de notre destin. Et ce message pourrait être envoyé par n'importe quel peuple européen, y compris le peuple français.", "french");
    train("Il en faut peu, parfois, pour passer du statut d'icône de la cause des femmes à celui de renégate. Lorsqu'elle a été nommée à la tête de Yahoo!, le 26 juillet 2012, Marissa Mayer était vue comme un modèle. Elle montrait qu'il était possible de perforer le fameux plafond de verre, même dans les bastions les mieux gardés du machisme (M du 28 juillet 2012). A 37 ans, cette brillante diplômée de Stanford, formée chez Google, faisait figure d'exemple dans la Silicon Valley californienne, où moins de 5 % des postes de direction sont occupés par des femmes. En quelques mois, le symbole a beaucoup perdu de sa puissance.", "french");
    train("Premier intervenant de taille à SXSW 2013, Bre Pettis, PDG de la société Makerbot, spécialisée dans la vente d'imprimantes 3D, a posé une question toute simple, avant de dévoiler un nouveau produit qui l'est un peu moins. Voulez-vous rejoindre notre environnement 3D ?, a-t-il demandé à la foule qui débordait de l'Exhibit Hall 5 du Convention Center.", "french");
    train("Des milliers de manifestants ont défilé samedi 9 mars à Tokyo pour exiger l'abandon rapide de l'énergie nucléaire au Japon, près de deux ans jour pour jour après le début de la catastrophe de Fukushima.", "french");
    train("Oui, ça en a tout l'air, même si le conflit en Syrie n'était pas confessionnel au départ et ne l'est pas encore vraiment. Il faut saluer là l'extraordinaire résistance de la société civile syrienne à la tentation confessionnelle, mais cela ne durera pas éternellement.", "french");

    // Spanish Training
    train("El ex presidente sudafricano, Nelson Mandela, ha sido hospitalizado la tarde del sábado, según confirmó un hospital de Pretoria a CNN. Al parecer se trata de un chequeo médico que ya estaba previsto, relacionado con su avanzada edad, según explicó el portavoz de la presidencia Sudafricana Mac Maharaj.", "spanish");
    train("Trabajadores del Vaticano escalaron al techo de la Capilla Sixtina este sábado para instalar la chimenea de la que saldrá el humo negro o blanco para anunciar el resultado de las votaciones para elegir al nuevo papa.La chimenea es el primer signo visible al público de las preparaciones que se realizan en el interior de la capilla donde los cardenales católicos se reunirán a partir de este martes para el inicio del cónclave.", "spanish");
    train("La Junta Directiva del Consejo Nacional Electoral (CNE) efectuará hoy una sesión extraordinaria para definir la fecha de las elecciones presidenciales, después de que Nicolás Maduro fuera juramentado ayer Viernes presidente de la República por la Asamblea Nacional.", "spanish");
    train("A 27 metros bajo el agua, la luz se vuelve de un azul profundo y nebuloso. Al salir de la oscuridad, tres bailarinas en tutú blanco estiran las piernas en la cubierta de un buque de guerra hundido. No es una aparición fantasmal, aunque lo parezca, es la primera de una serie de fotografías inolvidables que se muestran en la única galería submarina del mundo.", "spanish");
    train("Uhuru Kenyatta, hijo del líder fundador de Kenia, ganó por estrecho margen las elecciones presidenciales del país africano a pesar de enfrentar cargos de crímenes contra la humanidad por la violencia electoral de hace cinco años. Según anunció el sábado la comisión electoral, Kenyatta logró el 50,07% de los votos. Su principal rival, el primer ministro Raila Odinga, obtuvo 43,31% de los votos, y dijo que reclamará el resultado en los tribunales. La Constitución exige que el 50% más un voto para una victoria absoluta.", "spanish");

    // English Training
    train("One morning in late September 2011, a group of American drones took off from an airstrip the C.I.A. had built in the remote southern expanse of Saudi Arabia. The drones crossed the border into Yemen, and were soon hovering over a group of trucks clustered in a desert patch of Jawf Province, a region of the impoverished country once renowned for breeding Arabian horses.", "english");
    train("Just months ago, demonstrators here and around Egypt were chanting for the end of military rule. But on Saturday, as a court ruling about a soccer riot set off angry mobs, many in the crowd here declared they now believed that a military coup might be the best hope to restore order. Although such calls are hardly universal and there is no threat of an imminent coup, the growing murmurs that military intervention may be the only solution to the collapse of public security can be heard across the country, especially in circles opposed to the Islamists who have dominated post-Mubarak elections. ", "english");
    train("Syrian rebels released 21 detained United Nations peacekeepers to Jordanian forces on Saturday, ending a standoff that raised new tensions in the region and new questions about the fighters just as the United States and other Western nations were grappling over whether to arm them. The rebels announced the release of the Filipino peacekeepers, and Col. Arnulfo Burgos, a spokesman for the Armed Forces of the Philippines, confirmed it.", "english");
    train("The 83rd International Motor Show, which opened here last week, features the world premieres of 130 vehicles. These include an unprecedented number of models with seven-figure prices, including the $1.3 million LaFerrari supercar, the $1.15 million McLaren P1, the $1.6 million Koenigsegg Hundra and a trust-fund-busting Lamborghini, the $4 million Veneno. The neighborhood has become so rich that the new Rolls-Royce Wraith, expected to sell for more than $300,000, seemed, in comparison, like a car for the masses.", "english");
    train("David Hallberg, the statuesque ballet star who is a principal dancer at both the storied Bolshoi Ballet of Moscow and American Ballet Theater in New York, is theoretically the type of front-row coup that warrants a fit of camera flashes. But when Mr. Hallberg, 30, showed up at New York Fashion Week last month, for a presentation by the Belgian designer Tim Coppens, he glided into the front row nearly unnoticed, save for a quick chat with Tumblr’s fashion evangelist, Valentine Uhovski, and a warm embrace from David Farber, the executive style editor at WSJ.","english")

    println(guess("The Christchurch schoolboy whose emotional and inspiring speech after he was diagnosed with cancer went global is now in remission.One week before senior prizegiving, Christchurch Boys' High School senior monitor - head boy - Jake Bailey was diagnosed with Burkitt non-Hodgkin's lymphoma and told he might not live to see the day."))
    println(guess("Comme beaucoup de ses amis de la Nouvelle Vague, Jacques Rivette a d’abord été critique de cinéma. En 1950, il fonde avec Éric Rohmer, rencontré au ciné-club du Quartier Latin, la Gazette du cinéma, puis rejoint Les Cahiers du cinéma en 1953, avant d’en devenir rédacteur en chef dix ans plus tard. Entretemps, le critique est devenu cinéaste, avec la réalisation en 1958 de Paris nous appartient."))
  }

  def train(phrase: String, label: String): Unit = {
    // Register Label
    if (!labels.contains(label))
      labels +: label

    // Tokenize the words and increment
    tokenize(phrase).foreach(word => {
      if (wordCounts.contains(word))
        wordCounts.put(word, wordCounts(word) + 1)
      else
        wordCounts.put(word,1)

      if (wordLabelCounts.contains(s"$word::$label"))
        wordLabelCounts.put(s"$word::$label", wordLabelCounts(s"$word::$label") + 1)
      else
        wordLabelCounts.put(s"$word::$label", 1)
    })

    // Increment Document Count
    if (docCounts.contains(label))
      docCounts.put(label, docCounts(label) + 1)
    else
      docCounts.put(label, 1)
  }

  def calcInvDocCount(label: String): Double = {
    var rtCount = 0.0
    docCounts.map( {case (thisLabel, thisCount) => {
      if (!thisLabel.equals(label))
        rtCount += thisCount
    }})
    rtCount
  }

  def getWordLabelCounts(word: String, label: String): Double = {
    val key = s"$word::$label"
    if (wordLabelCounts.contains(key))
      wordLabelCounts(key)
    else
      0
  }

  def inverseWordLabelCount(word: String, label: String): Double = {
    var count = 0.0
    for (thisLabel <- docCounts.keys) {
      if (!thisLabel.equals(label)) {
        if (wordLabelCounts.contains(s"$word::$thisLabel")) {
          count += wordLabelCounts(s"$word::$thisLabel")
        }
      }
    }
    count
  }

  def guess(phrase: String): HashMap[String,Double] = {
    // Declare the variables
    val inverseWordLabelCounts = HashMap[String, Double]()
    val labelProbability = HashMap[String, Double]()
    val score = HashMap[String,Double]()
    val totalDocCounts = docCounts.values.reduce((a,b) => a + b)

    // Break down the words
    val words = tokenize(phrase)

    docCounts.foreach( {case (label,count) => {
      var logSum = 0.0
      labelProbability.put(label, count / totalDocCounts )

      words.foreach(word => {
        if (wordCounts.contains(word)) {
          val stemWC = wordCounts(word)
          var wordicity = 0.0

          val wordProbability = getWordLabelCounts(word,label) / docCounts(label)
          val invWordProbability = inverseWordLabelCount(word,label) / calcInvDocCount(label)
          wordicity = wordProbability / (wordProbability + invWordProbability)
          println("unmodified Wordicity: " + wordicity)

          wordicity = ((1 * 0.5) + (stemWC * wordicity)) / (1 + stemWC)
          if (wordicity == 0)
            wordicity = 0.01;
          else if (wordicity == 1)
            wordicity = 0.99;

          logSum = logSum + (Math.log(1 - wordicity) - Math.log(wordicity));
          println(label + "-icity of " + word + ": " + wordicity);
        }
      })
      score.put(label, (1 / ( 1 + Math.exp(logSum) )))
    }})
    score
  }

  def tokenize(phrase: String): Set[String] = {
    val wordsSet = phrase.toLowerCase.replaceAll("""[\p{Punct}]""", " ").trim.split(" ").toSet[String]
    wordsSet - ""   // Remove empty spaces
  }
}