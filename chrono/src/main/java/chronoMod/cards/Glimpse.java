package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.actions.MementoAction;
import chronoMod.characters.Chronomancer;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class Glimpse extends AbstractDynamicCard {
    public static final String ID = ChronoMod.makeID(Glimpse.class.getSimpleName());
    public static final String IMG = makeCardPath("Glimpse.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int COST = 0;
    private static final int CARD_DRAW = 1;
    private static final int SCRY_AMOUNT = 2;
    private static final int UPGRADE_PLUS_SCRY = 1;

    public Glimpse() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = SCRY_AMOUNT;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ScryAction(this.magicNumber));
        this.addToBot(new DrawCardAction(p, CARD_DRAW));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_SCRY);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Glimpse();
    }
}
