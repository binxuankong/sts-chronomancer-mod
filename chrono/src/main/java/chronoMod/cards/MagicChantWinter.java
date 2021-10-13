package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.actions.MagicChantWinterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class MagicChantWinter extends AbstractDynamicCard {
    private static final String CARD_ID = MagicChantWinter.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(CARD_ID);
    public static final String IMG = makeCardPath("skill/" + CARD_ID + ".png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    public MagicChantWinter() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // AbstractPower magicChant = p.getPower(MagicChantWinterPower.POWER_ID);
        // if (magicChant == null) {
        //    this.addToBot(new ApplyPowerAction(p, p, new MagicChantWinterPower(p)));
        // }
        this.addToBot(new MagicChantWinterAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MagicChantWinter();
    }
}
