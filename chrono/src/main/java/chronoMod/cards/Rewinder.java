package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.actions.GainJadeAction;
import chronoMod.actions.RewinderAction;
import chronoMod.characters.Chronomancer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class Rewinder extends AbstractDynamicCard {
    public static final String ID = ChronoMod.makeID(Rewinder.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int REWIND_COUNT = 3;
    private static final int JADE_AMOUNT = 2;

    public Rewinder() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new RewinderAction(REWIND_COUNT));
        this.addToBot(new GainJadeAction(JADE_AMOUNT));
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
    public AbstractDynamicCard makeCopy() {
        return new Rewinder();
    }
}
